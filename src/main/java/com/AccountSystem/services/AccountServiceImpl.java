package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Transaction;

import com.vikingbank.entities.User;

import com.vikingbank.repositories.AccountRepository;

import com.vikingbank.repositories.CustomAccountRepositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public class AccountServiceImpl implements AccountService {


    @Autowired

    private AccountRepository accountRepository;


    @Autowired

    private CustomAccountRepositoryImpl customAccountRepositoryImpl;


    @Override

    public List<Account> findAccountsByUser(User user) {

        return accountRepository.findAccountsByUser(user);

    }


    @Override

    public Optional<Account> findOneById(Long id) {

        return accountRepository.findOneById(id);

    }


    @Override

    public Optional<Account> findOneByAccountNumber(String accountNumber) {

        return accountRepository.findOneByAccountNumber(accountNumber);

    }


    @Override

    public boolean validate(List<Account> accounts, Transaction transaction) throws Exception {

        if ((accounts.stream().noneMatch(account1 -> transaction.getForeignAccount().getId().equals(account1.getId()) || transaction.getOwnerAccount().getId().equals(account1.getId())))) {

            throw new Exception("No transaction with that id found");

        }

        return true;

    }


    @Override

    public boolean transferFundsValidation(Transaction transaction, Long userId) {

        return transaction.getOwnerAccount().getId().equals(userId);

    }


    @Override

    public Account save(Account account) {

        return accountRepository.save(account);

    }


    @Override

    public List<Account> list(String name) {

        return customAccountRepositoryImpl.listAccounts(name);

    }

}
