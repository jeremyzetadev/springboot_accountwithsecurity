package com.vikingbank.configs;


import com.vikingbank.controller.*;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;


@Configuration

public class LoggerConfiguration {

    @Bean

    public Logger accountLogger() {

        return LoggerFactory.getLogger(AccountController.class);

    }


    @Bean

    public Logger invoiceLogger() {

        return LoggerFactory.getLogger(InvoiceController.class);

    }


    @Bean

    public Logger statementLogger() {

        return LoggerFactory.getLogger(StatementController.class);

    }


    @Bean

    public Logger userLogger() {

        return LoggerFactory.getLogger(UserController.class);

    }


    @Bean

    public Logger userRegistrationLogger() {

        return LoggerFactory.getLogger(UserRegistrationController.class);

    }


    @Bean

    public Logger transferLogger() {

        return LoggerFactory.getLogger(TransferController.class);

    }

}
