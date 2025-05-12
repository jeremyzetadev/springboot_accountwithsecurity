package com.vikingbank.controller;


import com.vikingbank.entities.*;

import com.vikingbank.exceptions.AccountNotFoundException;

import com.vikingbank.exceptions.IllegalTransferException;

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

import org.springframework.web.bind.annotation.PostMapping;


import java.math.BigDecimal;

import java.util.Date;

import java.util.List;

import java.util.Optional;


@Controller

public class TransferController extends BaseController {

    @Autowired

    @Qualifier("transferLogger")

    private Logger logger;

    @Autowired

    private UserService userService;

    @Autowired

    private AccountService accountService;

    @Autowired

    private TransactionService transactionService;


    @GetMapping("/transfers")

    public String index(Model model) throws Exception {

        Optional<User> userOptional = userService.findOneByEmail(getCurrentUser(getAuthentication(), userService).getEmail());

        if (userOptional.isEmpty()) {

            throw new UserNotFoundException();

        }

        List<Account> accounts = userOptional.get().getAccounts();

        model.addAttribute("accounts", accounts);

        TransactionRequest request = new TransactionRequest();

        model.addAttribute("transactionrequest", request);


        model.addAttribute("isAdmin", isAdmin());

        return "Transfer/create";

    }


    @PostMapping("/transfer")

    public String transfer(TransactionRequest transaction, Model model) throws Exception {


        Optional<Account> accountOptional = accountService.findOneByAccountNumber(transaction.getDestination());

        if (accountOptional.isEmpty()) {

            throw new AccountNotFoundException();

        }

        Optional<Account> accountOptionalOwner = accountService.findOneByAccountNumber(transaction.getOwner());

        if (accountOptionalOwner.isEmpty()) {

            throw new AccountNotFoundException();

        }


        Transaction transactionNew;

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {

            transactionNew = new Transaction(

                    accountOptionalOwner.get(), accountOptional.get(), transaction.getAmount(), transaction.getDescription(), new Date(), TransactionType.PAYMENT);

        } else {

            transactionNew = new Transaction(

                    accountOptionalOwner.get(), accountOptional.get(), transaction.getAmount(), transaction.getDescription(), new Date(), TransactionType.WITHDRAWAL);

        }


        if (!accountService.transferFundsValidation(transactionNew, getCurrentUser(getAuthentication(), userService).getId())) {

            throw new IllegalTransferException();

        }

        Transaction savedTransaction = transactionService.save(transactionNew);

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "A transfer has been made.");


        model.addAttribute("isAdmin", isAdmin());

        model.addAttribute("transaction", savedTransaction);

        return "Transaction/view";

    }

}
