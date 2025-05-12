

package com.vikingbank.rest.objects;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


import java.io.Serializable;


@JacksonXmlRootElement(localName = "user")

public class RestUserXML implements Serializable {


    @JacksonXmlProperty

    private String firstName;

    @JacksonXmlProperty

    private String lastName;

    @JacksonXmlProperty

    private String email;

    @JacksonXmlProperty

    private final boolean isUsing2FA = false;

    public RestUserXML() {


    }

    public RestUserXML(String firstName, String lastName, String email) {

        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email;

    }

}
