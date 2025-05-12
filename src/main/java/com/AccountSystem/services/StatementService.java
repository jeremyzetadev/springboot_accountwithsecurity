package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Statement;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public interface StatementService {

    List<Statement> findByAccount(Account account);


    Optional<Statement> findOneById(Long id);

}
