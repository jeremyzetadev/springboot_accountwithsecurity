package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Transaction;

import com.vikingbank.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public class TransactionServiceImpl implements TransactionService {


    @Autowired

    private TransactionRepository transactionRepository;


    @Override

    public List<Transaction> findByOwnerAccount(Account account) {

        return transactionRepository.findByOwnerAccount(account);

    }


    @Override

    public Transaction save(Transaction transaction) {

        return transactionRepository.save(transaction);

    }


    @Override

    public Optional<Transaction> findOneById(Long id) {

        return transactionRepository.findOneById(id);

    }


    @Override

    public Integer sumOfTransactions(Account account) {

        return transactionRepository.sumOfTransactionsFromOwner(account) + transactionRepository.sumOfTransactionsFromForeign(account);

        // from owner -> tends to be expenses. from foreign -> tends to be incomming. Added together, should result in the account balance.

    }

}
