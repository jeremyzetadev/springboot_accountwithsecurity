package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Statement;

import com.vikingbank.repositories.StatementRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service()


public class StatementServiceImpl implements StatementService {


    @Autowired

    private StatementRepository statementRepository;


    @Override

    public List<Statement> findByAccount(Account account) {

        return statementRepository.findByAccount(account);

    }


    @Override

    public Optional<Statement> findOneById(Long id) {

        return statementRepository.findById(id);

    }

}
