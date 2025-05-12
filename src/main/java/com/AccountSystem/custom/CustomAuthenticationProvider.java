package com.vikingbank.custom;


import com.amdelamar.jotp.OTP;

import com.amdelamar.jotp.type.Type;

import com.vikingbank.entities.User;

import com.vikingbank.exceptions.UserNotFoundException;

import com.vikingbank.services.UserService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.IOException;

import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;


public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired

    @Qualifier("userLogger")

    private Logger userLogger;


    @Autowired

    private UserService userService;


    @Autowired

    @Qualifier("passwordEncoder")

    private PasswordEncoder passwordEncoder;


    @Override

    public Authentication authenticate(Authentication authentication) {


        try {

            CustomAuthenticationDetails userProvidedLoginDetails = (CustomAuthenticationDetails) authentication.getDetails();

            String password = (String) authentication.getCredentials();


            UserDetails userDetails = userService.loadUserByUsername(authentication.getName());

            if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {

                User user = userService.findOneByEmail(userDetails.getUsername()).get();

                String serverGeneratedCode = OTP.create(user.getSecret(), OTP.timeInHex(), 6, Type.TOTP);

                if (!serverGeneratedCode.equals(userProvidedLoginDetails.getUser2FaCode())) {

                    throw new UserNotFoundException();

                }


                userLogger.info("a login took place");

                return new UsernamePasswordAuthenticationToken(user.getEmail(), password, userDetails.getAuthorities());

            } else {

                throw new UserNotFoundException();

            }

        } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {

            throw new RuntimeException(e.getMessage(), e);

        }

    }


    @Override

    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);

    }

}
