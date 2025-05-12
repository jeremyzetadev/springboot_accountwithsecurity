package com.vikingbank.controller;


import com.vikingbank.entities.*;

import com.vikingbank.exceptions.AccountNotFoundException;

import com.vikingbank.exceptions.TransactionNotFoundException;

import com.vikingbank.exceptions.UserNotFoundException;

import com.vikingbank.services.AccountService;

import com.vikingbank.services.TransactionService;

import com.vikingbank.services.UserService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

import java.util.Optional;


@Controller

public class AccountController extends BaseController {

    @Autowired

    @Qualifier("accountLogger")

    private Logger logger;

    @Autowired

    private AccountService accountService;

    @Autowired

    private UserService userService;

    @Autowired

    private TransactionService transactionService;


    @GetMapping("/accounts")

    public String index(Model model) throws Exception {

        // logged in user

        User user = getCurrentUser(getAuthentication(), userService);

        List<Account> accounts = accountService.findAccountsByUser(user);

        accounts.forEach(account -> {

            account.setCalculatedBalance(transactionService.sumOfTransactions(account));

        });

        model.addAttribute("user", user);

        if (accounts.size() != 0) {

            model.addAttribute("accounts", accounts);


        } else {

            model.addAttribute("noaccounts", true);

        }

        model.addAttribute("isAdmin", isAdmin());

        return "Account/index";

    }


    @GetMapping("/accounts/{id}")

    public String detail(@PathVariable(name = "id") Long id, Model model) throws Exception {

        User user = getCurrentUser(getAuthentication(), userService);

        Optional<Account> accountOptional = accountService.findOneById(id);

        if (accountOptional.isEmpty()) {

            throw new AccountNotFoundException();

        }

        Account account = accountOptional.get();

        if (!userService.validate(account.getId(), user)) {

            l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "logged userid tried to view account details for " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new AccountNotFoundException();

        }


        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "accessed account detail page of " + id);

        model.addAttribute("account", account);

        model.addAttribute("isAdmin", isAdmin());

        return "Account/view";

    }


    @GetMapping("/accounts/{id}/transaction/{transactionId}")

    public String viewTransaction(@PathVariable(name = "id") Long id, @PathVariable(name = "transactionId") Long transactionId, Model model) throws Exception {

        User user = getCurrentUser(getAuthentication(), userService);

        Optional<Account> accountOptional = accountService.findOneById(id);

        if (accountOptional.isEmpty()) {

            throw new AccountNotFoundException();

        }

        Account account = accountOptional.get();

        Optional<Transaction> transaction = transactionService.findOneById(transactionId);

        if (transaction.isEmpty()) {

            throw new TransactionNotFoundException();

        }

        if (userService.validate(account.getId(), user)) {

            model.addAttribute("account", account);

        } else {

            l(logger, Severity.ERROR, LogType.AUDIT_LOG, "logged userid tried to view transaction details of account " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotFoundException();

        }


        if (!accountService.validate(user.getAccounts(), transaction.get())) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to view transaction details of account that doesn't belong to them " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new TransactionNotFoundException();

        }

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "accessed account detail page of " + id);

        model.addAttribute("transaction", transaction);

        model.addAttribute("isAdmin", isAdmin());

        return "Transaction/view";

    }

}
