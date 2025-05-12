package com.vikingbank.repositories;


import com.vikingbank.entities.Account;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;


import java.util.List;


@Repository

public class CustomAccountRepositoryImpl implements CustomAccountRepository {

    @PersistenceContext

    private EntityManager entityManager;


    @Override

    public List<Account> listAccounts(String name) {

        return entityManager.createNativeQuery("select a from Account a join User u " +

                "where u.id = a.user_id and u.name = :name", Account.class).setParameter("name", name).getResultList();

    }

}
