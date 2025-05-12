package com.vikingbank.services;


import com.vikingbank.entities.User;

import com.vikingbank.exceptions.InvalidUserException;

import com.vikingbank.rest.objects.RestUserXML;

import com.vikingbank.rest.objects.RestUsersXML;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;

import org.springframework.stereotype.Service;

import org.springframework.util.StreamUtils;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

import org.xml.sax.SAXException;


import javax.xml.XMLConstants;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.stream.XMLInputFactory;

import java.io.IOException;

import java.io.StringReader;

import java.nio.charset.Charset;

import java.util.List;

import java.util.stream.Collectors;


@Service

public class XMLExportServiceImpl implements XMLExportService {


    @Value("users.xml.dir")

    private String usersXMLPath;


    @Autowired

    private UserService userService;


    @Override

    public RestUsersXML exportUsers(List<User> users) {

        return new RestUsersXML(users.stream().map(baseUser -> new RestUserXML(baseUser.getFirstName(), baseUser.getLastName(), baseUser.getEmail())).collect(Collectors.toList()));

    }


    public String readFileToString(String path) throws IOException {

        return StreamUtils.copyToString(new ClassPathResource(path).getInputStream(), Charset.defaultCharset());

    }


    @Override

    public void importUsers() throws ParserConfigurationException, IOException, SAXException {

        InputSource inputXml = new InputSource(new StringReader(readFileToString(usersXMLPath)));


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        documentBuilderFactory.setFeature(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);

        documentBuilderFactory.setFeature(XMLInputFactory.SUPPORT_DTD, false);

        documentBuilderFactory.setXIncludeAware(false);

        documentBuilderFactory.setExpandEntityReferences(false);


        DocumentBuilder safebuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = safebuilder.parse(inputXml);

        document.getDocumentElement().normalize();

        NodeList list = document.getElementsByTagName("user");

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);


            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;


                String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();

                String lastName = element.getElementsByTagName("lastname").item(0).getTextContent();

                String email = element.getElementsByTagName("email").item(0).getTextContent();

                if (firstname.length() > 30 || firstname.isEmpty()) {

                    throw new InvalidUserException();

                }


                if (lastName.length() > 30 || lastName.isEmpty()) {

                    throw new InvalidUserException();

                }


                if (email.length() > 30 || email.isEmpty() || !email.matches("^(.+)@(.+)$")) {

                    throw new InvalidUserException();

                }


                userService.saveBaseUser(new User(firstname, lastName, email));

            }

        }

    }

}
