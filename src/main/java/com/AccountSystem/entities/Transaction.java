package com.vikingbank.entities;


import javax.persistence.*;

import java.math.BigDecimal;

import java.util.Date;


@Entity(name = "table_transaction")

public class Transaction {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne

    private Account ownerAccount;

    @ManyToOne

    private Account foreignAccount;

    private BigDecimal amount;

    private String description;

    private Date timestamp;

    @Enumerated(EnumType.ORDINAL)

    private TransactionType transactionType;

    @OneToOne

    private Invoice invoice;


    public Transaction() {

    }


    public Transaction(Account ownerAccount, Account foreignAccount, BigDecimal amount, String description, Date timestamp, TransactionType transactionType) {

        this.ownerAccount = ownerAccount;

        this.foreignAccount = foreignAccount;

        this.amount = amount;

        this.description = description;

        this.timestamp = timestamp;

        this.transactionType = transactionType;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public Account getOwnerAccount() {

        return ownerAccount;

    }


    private void setOwnerAccount(Account ownerAccount) {

        this.ownerAccount = ownerAccount;

    }


    public Account getForeignAccount() {

        return foreignAccount;

    }


    private void setForeignAccount(Account foreignAccount) {

        this.foreignAccount = foreignAccount;

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


    public Date getTimestamp() {

        return timestamp;

    }


    private void setTimestamp(Date timestamp) {

        this.timestamp = timestamp;

    }


    public TransactionType getTransactionType() {

        return transactionType;

    }


    private void setTransactionType(TransactionType transactionType) {

        this.transactionType = transactionType;

    }


    public Invoice getInvoice() {

        return invoice;

    }


    private void setInvoice(Invoice invoice) {

        this.invoice = invoice;

    }

}
