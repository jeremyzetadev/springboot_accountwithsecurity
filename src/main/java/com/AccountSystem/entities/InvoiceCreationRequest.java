package com.vikingbank.entities;


public class InvoiceCreationRequest {

    private String dueDate;

    private String buyerNumber;

    private String description;

    private Integer amount;


    public InvoiceCreationRequest() {

    }


    public InvoiceCreationRequest(String dueDate, String buyerNumber, String description, Integer amount) {

        this.dueDate = dueDate;

        this.buyerNumber = buyerNumber;

        this.description = description;

        this.amount = amount;

    }


    public String getDueDate() {

        return dueDate;

    }


    private void setDueDate(String dueDate) {

        this.dueDate = dueDate;

    }


    public String getBuyerNumber() {

        return buyerNumber;

    }


    private void setBuyerNumber(String buyerNumber) {

        this.buyerNumber = buyerNumber;

    }


    public String getDescription() {

        return description;

    }


    private void setDescription(String description) {

        this.description = description;

    }


    public Integer getAmount() {

        return amount;

    }


    private void setAmount(Integer amount) {

        this.amount = amount;

    }


}
