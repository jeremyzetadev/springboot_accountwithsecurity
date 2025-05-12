package com.vikingbank.entities;


import javax.persistence.*;

import java.math.BigDecimal;

import java.util.Date;


@Entity

public class Invoice {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private BigDecimal amount;

    @OneToOne

    private Account buyerAccount;

    @OneToOne

    private Account sellerAccount;

    private String description;

    private Date dueDate;

    private boolean paymentStatus = false;


    public Invoice() {

    }


    public Invoice(BigDecimal amount, Account buyerAccount, Account sellerAccount, String description, Date dueDate, boolean paymentStatus) {

        this.amount = amount;

        this.buyerAccount = buyerAccount;

        this.sellerAccount = sellerAccount;

        this.description = description;

        this.dueDate = dueDate;

        this.paymentStatus = paymentStatus;

    }


    public void updatePaymentStatus() {

        this.paymentStatus = true;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public BigDecimal getAmount() {

        return amount;

    }


    private void setAmount(BigDecimal amount) {

        this.amount = amount;

    }


    public Account getBuyerAccount() {

        return buyerAccount;

    }


    private void setBuyerAccount(Account buyerAccount) {

        this.buyerAccount = buyerAccount;

    }


    public Account getSellerAccount() {

        return sellerAccount;

    }


    private void setSellerAccount(Account sellerAccount) {

        this.sellerAccount = sellerAccount;

    }


    public String getDescription() {

        return description;

    }


    private void setDescription(String description) {

        this.description = description;

    }


    public Date getDueDate() {

        return dueDate;

    }


    private void setDueDate(Date dueDate) {

        this.dueDate = dueDate;

    }


    public boolean isPaymentStatus() {

        return paymentStatus;

    }


    private void setPaymentStatus(boolean paymentStatus) {

        this.paymentStatus = paymentStatus;

    }


    public String toString() {

        return description;

    }

}
