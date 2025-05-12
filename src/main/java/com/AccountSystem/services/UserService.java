package com.vikingbank.services;


import com.vikingbank.controller.dto.UserRegistrationDto;

import com.vikingbank.entities.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.util.List;

import java.util.Optional;


public interface UserService extends UserDetailsService {


    User save(UserRegistrationDto registrationDto);


    Optional<User> findOneByEmail(String email);


    List<User> findAll();


    User saveBaseUser(User user);


    User changePassword(User user, String password);


    Optional<User> findOneById(Long id);


    String configure2FA(User user) throws UnsupportedEncodingException;


    boolean validate(Long id, User user) throws Exception;


    boolean validatePasswordComplexity(String password);


    String checkUploadedImage(MultipartFile file, User user) throws Exception;


    void importUserSettings(String cookieValue, User currentUser) throws IOException;


    String exportUserSettings(User user);


    void downloadRemoteImage(User user, String url) throws Exception;

}
