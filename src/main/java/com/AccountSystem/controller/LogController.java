package com.vikingbank.controller;


import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import com.vikingbank.services.LogService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class LogController extends BaseController {

    @Autowired

    private LogService logService;


    @Autowired

    @Qualifier("userLogger")

    private Logger userLogger;


    @GetMapping("/logs")

    public String index(Model model) throws Exception {

        // make sure we've gotten the proper rights

        model.addAttribute("isAdmin", isAdmin());

        l(userLogger, Severity.DEBUG, LogType.AUDIT_LOG, "viewed logs page");

        model.addAttribute("logs", logService.getLogs()); //customize query later


        return "Logs/index";

    }

}
