package com.vikingbank.controller;


import com.vikingbank.controller.dto.UserRegistrationDto;

import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import com.vikingbank.entities.User;

import com.vikingbank.exceptions.UserNotFoundException;

import com.vikingbank.services.UserService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;

import java.util.Optional;


@Controller

@RequestMapping("/registration")

public class UserRegistrationController extends BaseController {

    private final UserService userService;

    @Autowired

    @Qualifier("userRegistrationLogger")

    private Logger logger;


    public UserRegistrationController(UserService userService) {

        super();

        this.userService = userService;

    }


    @ModelAttribute("user")

    public UserRegistrationDto userRegistrationDto() {

        return new UserRegistrationDto();

    }


    @GetMapping()

    public String showRegistrationForm(Model model) throws Exception {

        return "registration";

    }


    @PostMapping()

    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {

        User user = userService.save(registrationDto);

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "a new user has registered");

        return "redirect:/registration/configure2fa/" + user.getId();

    }


    @GetMapping("/configure2fa/{id}")

    public String viewConfigure2FA(Model model, @PathVariable(value = "id") Long id) throws UnsupportedEncodingException {

        Optional<User> userOptional = userService.findOneById(id);

        if (userOptional.isEmpty()) {

            l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "logged userid tried to configure 2fa, but the user wasnt found");

            throw new UserNotFoundException();

        }

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "2fa is being set up for " + id);

        model.addAttribute("twoFaQrUrl", userService.configure2FA(userOptional.get()));

        model.addAttribute("config@FA", true);

        return "config2fa";

    }

}
