package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Transaction;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public interface TransactionService {


    List<Transaction> findByOwnerAccount(Account account);


    Transaction save(Transaction tRansaction);


    Optional<Transaction> findOneById(Long id);


    Integer sumOfTransactions(Account account);

}
