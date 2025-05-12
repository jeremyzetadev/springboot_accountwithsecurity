package com.vikingbank.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)

public class CreateAccountRequest implements Serializable {

    private String name;


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }

}
