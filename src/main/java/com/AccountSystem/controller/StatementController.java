package com.vikingbank.controller;


import com.vikingbank.entities.*;

import com.vikingbank.exceptions.StatementNotFoundException;

import com.vikingbank.services.CSVExportService;

import com.vikingbank.services.StatementService;

import com.vikingbank.services.TransactionService;

import com.vikingbank.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;

import java.util.List;

import java.util.Optional;


@Controller

public class StatementController extends BaseController {

    @Autowired

    @Qualifier("statementLogger")

    private Logger logger;

    @Autowired

    private StatementService statementService;


    @Autowired

    private TransactionService transactionService;


    @Autowired

    private UserService userService;


    @Autowired

    private CSVExportService csvExportService;


    @GetMapping("/statements")

    public String index(Model model) throws Exception {

        User user = getCurrentUser(getAuthentication(), userService);

        List<Statement> statements = new ArrayList<>();

        user.getAccounts().forEach(account -> {

            statements.addAll(statementService.findByAccount(account));

        });

        if (statements.size() != 0) {

            model.addAttribute("statements", statements);

        } else {

            model.addAttribute("statementnotfound", true);

        }

        model.addAttribute("isAdmin", isAdmin());


        return "Statement/index";

    }


    @GetMapping("/statements/{id}")

    public String detail(Model model, @PathVariable(value = "id") Long id) throws Exception {

        Statement statement = findOneById(id);


        if (!userService.validate(statement.getAccount().getId(), getCurrentUser(getAuthentication(), userService))) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to view a statement that doesn't involve them " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new StatementNotFoundException();

        }

        List<Transaction> transactions = transactionService.findByOwnerAccount(statement.getAccount());

        if (transactions.size() == 0) {

            model.addAttribute("statementnotfound", true);

        } else {

            model.addAttribute("transactions", transactions);

            model.addAttribute("statement_id", statement.getId());

        }

        model.addAttribute("isAdmin", isAdmin());


        return "Statement/view";

    }


    private Statement findOneById(Long id) {

        Optional<Statement> statementOptional = statementService.findOneById(id);


        if (statementOptional.isEmpty()) {

            throw new StatementNotFoundException();

        }


        return statementOptional.get();

    }


    @RequestMapping(value = "/statements/download/{id}", produces = "text/csv")

    public void download(@PathVariable(value = "id") Long id, HttpServletResponse response) throws Exception {

        Statement statement = findOneById(id);

        if (!userService.validate(statement.getAccount().getId(), getCurrentUser(getAuthentication(), userService))) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to download astatement that doesn't involve them " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new StatementNotFoundException();

        }


        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "user downloaded statement " + id);

        List<Transaction> transactions = transactionService.findByOwnerAccount(statement.getAccount());

        csvExportService.exportStatement(transactions, response);

    }

}
