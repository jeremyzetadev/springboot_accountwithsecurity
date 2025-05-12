package com.vikingbank.entities;


import java.math.BigDecimal;


public class TransactionRequest {

    private String destination;

    private String owner;

    private BigDecimal amount;

    private String description;


    public TransactionRequest() {

    }


    public TransactionRequest(String destination, String owner, BigDecimal amount, String description) {

        this.destination = destination;

        this.owner = owner;

        this.amount = amount;

        this.description = description;

    }


    public String getOwner() {

        return owner;

    }


    private void setOwner(String owner) {

        this.owner = owner;

    }


    public String getDestination() {

        return destination;

    }


    private void setDestination(String destination) {

        this.destination = destination;

    }


    public BigDecimal getAmount() {

        return amount;

    }


    private void setAmount(BigDecimal amount) {

        this.amount = amount;

    }


    public String getDescription() {

        return description;

    }


    private void setDescription(String description) {

        this.description = description;

    }

}
