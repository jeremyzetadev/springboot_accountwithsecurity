

package com.vikingbank.repositories;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;


import java.util.List;

import java.util.Optional;


@Repository

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOwnerAccount(Account account);


    Optional<Transaction> findOneById(Long id);


    @Query(value = "SELECT SUM(t.amount) FROM table_transaction AS t WHERE t.ownerAccount = ?1")

    Integer sumOfTransactionsFromOwner(Account account);


    @Query(value = "SELECT SUM(t.amount) FROM table_transaction AS t WHERE t.foreignAccount = ?1")

    Integer sumOfTransactionsFromForeign(Account account);


}
