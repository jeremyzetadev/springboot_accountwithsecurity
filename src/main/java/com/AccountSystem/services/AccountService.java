package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Transaction;

import com.vikingbank.entities.User;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public interface AccountService {

    List<Account> findAccountsByUser(User id);


    Optional<Account> findOneById(Long id);


    Optional<Account> findOneByAccountNumber(String accountNumber);


    boolean validate(List<Account> accounts, Transaction transaction) throws Exception;


    boolean transferFundsValidation(Transaction transaction, Long userId);


    Account save(Account account);


    List<Account> list(String name);

}
