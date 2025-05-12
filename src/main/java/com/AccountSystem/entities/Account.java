package com.vikingbank.entities;


import jakarta.persistence.*;


import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;

import java.util.List;


@Entity

public class Account {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne

    private User user;

    private String name;

    @Column(unique = true)

    private String accountNumber;


    /**

     * calculated balance, not a static value.

     */

    private Integer balance;

    @OneToMany

    private List<Transaction> transactions;

    @OneToMany

    private List<Statement> statements;

    @OneToMany

    private List<Invoice> sentInvoices;

    @OneToMany

    private List<Invoice> receivedInvoices;



    public Account(User user, String name, String accountNumber, Integer balance, List<Transaction> transactions, List<Statement> statements, List<Invoice> sentInvoices, List<Invoice> receivedInvoices) {

        this.user = user;

        this.name = name;

        this.accountNumber = accountNumber;

        this.balance = balance;

        this.transactions = transactions;

        this.statements = statements;

        this.sentInvoices = sentInvoices;

        this.receivedInvoices = receivedInvoices;

    }


    public Account() {


    }


    public void generateAccountNumber() throws NoSuchAlgorithmException {

        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < 16; i++) {

            if (i % 4 == 0) {

                sequence.append("-");

            }

            int n = SecureRandom.getInstanceStrong().nextInt(10);

            sequence.append(n);

        }

        this.accountNumber = sequence.toString();

    }


    public void setCustomName(String customName) {

        this.name = customName;

    }


    public void setCalculatedBalance(Integer balance) {

        this.balance = balance;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public User getUser() {

        return user;

    }


    private void setUser(User user) {

        this.user = user;

    }


    public String getName() {

        return name;

    }


    private void setName(String name) {

        this.name = name;

    }


    public String getAccountNumber() {

        return accountNumber;

    }


    private void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;

    }


    public Integer getBalance() {

        return balance;

    }


    private void setBalance(Integer balance) {

        this.balance = balance;

    }


    public List<Transaction> getTransactions() {

        return transactions;

    }


    private void setTransactions(List<Transaction> transactions) {

        this.transactions = transactions;

    }


    public List<Statement> getStatements() {

        return statements;

    }


    private void setStatements(List<Statement> statements) {

        this.statements = statements;

    }


    public List<Invoice> getSentInvoices() {

        return sentInvoices;

    }


    private void setSentInvoices(List<Invoice> sentInvoices) {

        this.sentInvoices = sentInvoices;

    }


    public List<Invoice> getReceivedInvoices() {

        return receivedInvoices;

    }


    private void setReceivedInvoices(List<Invoice> receivedInvoices) {

        this.receivedInvoices = receivedInvoices;

    }


    public String toString() {

        return name;

    }

}
