

package com.vikingbank.rest.objects;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


import java.io.Serializable;

import java.util.List;


@JacksonXmlRootElement(localName = "user_list")

public class RestUsersXML implements Serializable {

    @JacksonXmlProperty

    private List<RestUserXML> restUserXMLList;


    public RestUsersXML(List<RestUserXML> list) {

        this.restUserXMLList = list;

    }


    public List<RestUserXML> getRestUserXMLList() {

        return restUserXMLList;

    }


    public void setRestUserXMLList(List<RestUserXML> restUserXMLList) {

        this.restUserXMLList = restUserXMLList;

    }

}
