

package com.vikingbank.repositories;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Statement;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;

import java.util.Optional;


@Repository

public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findByAccount(Account account);


    Optional<Statement> findOneById(Long id);


}
