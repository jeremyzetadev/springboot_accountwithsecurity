package com.vikingbank.services;


import com.vikingbank.entities.User;

import com.vikingbank.rest.objects.RestUsersXML;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import java.util.List;


public interface XMLExportService {


    RestUsersXML exportUsers(List<User> users);


    void importUsers() throws ParserConfigurationException, IOException, SAXException;

}
