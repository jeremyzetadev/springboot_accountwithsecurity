package com.vikingbank.controller;


import com.vikingbank.entities.User;

import com.vikingbank.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class MainController extends BaseController {

    @Autowired

    private UserService userService;


    @GetMapping("/login")

    public String login() {

        return "login";

    }


    @GetMapping("/")

    public String home(Model model) throws Exception {

        model.addAttribute("isAdmin", isAdmin());

        User user = getCurrentUser(getAuthentication(), userService);


        if (!user.getSecret().isEmpty() && !user.isUsing2FA()) {

            user.update2FAUsage();

            userService.saveBaseUser(user);

        }

        return "index";

    }



}
