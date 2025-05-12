

package com.vikingbank.rest.objects;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize

public class RestAccount {

    private Long id;

    private String name;

    private String accountNumber;


    public RestAccount(Long id, String name, String accountNumber) {

        this.id = id;

        this.name = name;

        this.accountNumber = accountNumber;

    }


    public Long getId() {

        return id;

    }


    public void setId(Long id) {

        this.id = id;

    }


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getAccountNumber() {

        return accountNumber;

    }


    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;

    }

}
