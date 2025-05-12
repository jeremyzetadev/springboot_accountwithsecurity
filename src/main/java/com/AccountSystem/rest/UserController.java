package com.vikingbank.rest;


import com.fasterxml.jackson.databind.ser.Serializers;

import com.vikingbank.controller.BaseController;

import com.vikingbank.exceptions.FileSizeTooLargeException;

import com.vikingbank.services.UserService;

import com.vikingbank.services.XMLExportService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.util.ArrayList;

import java.util.List;


@RestController

public class UserController extends BaseController {


    @Value("${upload.dir}")

    private String location;


    @Autowired

    @Qualifier("userLogger")

    private Logger userLogger;


    @Autowired

    private XMLExportService xmlExportService;


    @Autowired

    private UserService userService;


    @GetMapping(value = "/")

    public ResponseEntity<InputStreamResource> downloadProfilePicture() throws IOException {

        List<String> allowed = new ArrayList<>();

        allowed.add("image/png");

        allowed.add("image/jpg");


        String filename = getCurrentUser(getAuthentication(), userService).getProfilePicture();

        String mimeType = Files.probeContentType(Path.of(filename));


        if (allowed.stream().noneMatch(mimeType.toLowerCase()::equals)) {

            throw new FileNotFoundException();

        }


        if ((new File(filename).length() / 1024 / 1024) > 5) {

            throw new FileSizeTooLargeException();

        }


        InputStreamResource resource = new InputStreamResource(new FileInputStream(Path.of(location + filename).normalize().toAbsolutePath().toString()));


        if (!resource.getFile().getAbsolutePath().matches(location + "([a-zA-Z0-9]){10}(\\.png|\\.jpg\\.jpeg\\.JPEG\\.JPG\\.PNG)")) {

            userLogger.error("probable path traversal detected, we've ended up outside of the profile picture upload directory" + filename);

            throw new FileNotFoundException();

        }


        return ResponseEntity.ok()

                .contentType(MediaType.IMAGE_JPEG)

                .contentLength(resource.contentLength())

                .contentType(MediaType.APPLICATION_OCTET_STREAM)

                .body(resource);

    }


    @GetMapping(value = "/rest/export/users", produces = MediaType.APPLICATION_XML_VALUE)

    public void exportUsersXML() {

        xmlExportService.exportUsers(userService.findAll());

    }


    @PostMapping(value = "/rest/import/users")

    public void importUsers() throws ParserConfigurationException, IOException, SAXException {

        xmlExportService.importUsers();

    }

}
