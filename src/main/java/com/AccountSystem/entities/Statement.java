package com.vikingbank.entities;


import javax.persistence.*;

import java.util.Date;


@Entity

public class Statement {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Date endDate;

    private String data;

    @ManyToOne

    private Account account;


    public Statement() {

    }


    public Statement(Date endDate, String data, Account account) {

        this.endDate = endDate;

        this.data = data;

        this.account = account;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public Date getEndDate() {

        return endDate;

    }


    private void setEndDate(Date endDate) {

        this.endDate = endDate;

    }


    public String getData() {

        return data;

    }


    private void setData(String data) {

        this.data = data;

    }


    public Account getAccount() {

        return account;

    }


    private void setAccount(Account account) {

        this.account = account;

    }

}
