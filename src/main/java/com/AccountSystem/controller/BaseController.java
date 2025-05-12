package com.vikingbank.controller;


import com.amdelamar.jotp.OTP;

import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import com.vikingbank.entities.User;

import com.vikingbank.exceptions.UserNotFoundException;

import com.vikingbank.services.LogService;

import com.vikingbank.services.UserService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;


public class BaseController {

    @Autowired

    private UserService userService;


    @Autowired

    private LogService logService;


    @Autowired

    @Qualifier("userLogger")

    private Logger userLogger;


    public User getCurrentUser(Authentication authentication, UserService userService) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> userOptional = userService.findOneByEmail(userDetails.getUsername());

        if (userOptional.isEmpty()) {

            throw new UserNotFoundException();

        }

        User user = userOptional.get();

        if (user.getSecret() == null && user.isUsing2FA()) {

            l(userLogger, Severity.DEBUG, LogType.AUDIT_LOG, "generated user secret 2fa, because it was empty");

            user.setGeneratedSecret(OTP.randomBase32(20)); // create secret on the fly, if it doesnt exist yet.

            return userService.saveBaseUser(user);

        }

        return user;

    }


    public Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();

    }


    protected boolean isAdmin() throws Exception {

        return getCurrentUser(getAuthentication(), userService).getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

    }


    protected void l(Logger logger, Severity severity, LogType logType, String line) {

        switch (severity) {

            case INFO:

                logger.info(line);

                break;

            case WARN:

                logger.warn(line);

                break;

            case DEBUG:

                logger.debug(line);

                break;

            case ERROR:

            case FATAL:

                logger.error(line);

                break;

        }

        logService.log(severity, line, getCurrentUser(getAuthentication(), userService).getId(), logType);

    }


    protected void l(Logger logger, Severity severity, LogType logType, String line, Long loggedInUserId) {

        line = "logged in user: " + loggedInUserId + line;

        l(logger, severity, logType, line);

    }

}
