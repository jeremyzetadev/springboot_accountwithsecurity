package com.vikingbank.rest;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.CreateAccountRequest;

import com.vikingbank.exceptions.UserNotAuthorizedException;

import com.vikingbank.rest.objects.ListAccountsResponse;

import com.vikingbank.rest.objects.RestAccount;

import com.vikingbank.services.AccountService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;


import java.security.NoSuchAlgorithmException;

import java.util.List;

import java.util.logging.Logger;

import java.util.stream.Collectors;


@RestController

public class AccountAPIController {


    @Autowired

    @Qualifier("accountLogger")

    private Logger accountLogger;

    @Autowired

    private AccountService accountService;


    @PostMapping("/api/account/new")

    public void createAccount(CreateAccountRequest createAccountRequest) throws NoSuchAlgorithmException {

        Account account = new Account();

        account.setCustomName(createAccountRequest.getName());

        account.setCalculatedBalance(0);

        account.generateAccountNumber();

        accountService.save(account);

    }


    @GetMapping("/api/account/overview/{name}")

    public ListAccountsResponse listAccounts(HttpServletRequest request, @PathVariable(value = "name") String name) {

        // only allow server admins access

        if (!request.getRemoteAddr().equals("127.0.0.1")) {

            throw new UserNotAuthorizedException();

        }

        List<Account> accountList = accountService.list(name);

        return new ListAccountsResponse(accountList.stream().map(accountEntity -> new RestAccount(accountEntity.getId(), accountEntity.getName(), accountEntity.getAccountNumber())).collect(Collectors.toList()));

    }

}
