package com.vikingbank.controller;


import com.vikingbank.entities.ChangePasswordRequest;

import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import com.vikingbank.entities.User;

import com.vikingbank.exceptions.UserNotAuthorizedException;

import com.vikingbank.exceptions.UserNotFoundException;

import com.vikingbank.services.CSVExportService;

import com.vikingbank.services.UserService;

import com.vikingbank.services.XMLExportService;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.security.MD5Encoder;

import org.bouncycastle.crypto.digests.MD5Digest;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.List;

import java.util.Optional;


@Controller

public class UserController extends BaseController {

    @Autowired

    @Qualifier("userLogger")

    private Logger logger;

    @Autowired

    private UserService userService;


    @Autowired

    private CSVExportService csvExportService;


    @Autowired

    private XMLExportService xmlExportService;


    @GetMapping("/profile")

    public String viewProfile(Model model) throws Exception {

        User user = getCurrentUser(getAuthentication(), userService);

        model.addAttribute("user", user);

        model.addAttribute("changepasswordrequest", new ChangePasswordRequest());

        model.addAttribute("isAdmin", user.getRoles().stream().anyMatch(role -> role.equals("admin")));

        return "Users/UserDetails";

    }


    @GetMapping("/users/userDetails/{id}")

    public String viewUser(@PathVariable(value = "id") Long id, Model model) throws Exception {

        if (!hasAdminRights()) {

            l(logger, Severity.INFO, LogType.APPLICATION_LOG, "An unauthorized user tried to view other user's details");

            throw new UserNotAuthorizedException();

        }


        Optional<User> optionalUser = userService.findOneById(id);

        if (optionalUser.isEmpty()) {

            l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "tried to view a user that didn't exist. Automated scan?" + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotFoundException();

        }

        model.addAttribute("user", optionalUser.get());

        model.addAttribute("changepasswordrequest", new ChangePasswordRequest());

        model.addAttribute("isAdmin", isAdmin());

        return "Users/UserDetails";

    }


    @GetMapping("/users")

    public String overview(Model model) throws Exception {

        if (!hasAdminRights()) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to view user overview, but they dont have admin rights", getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotAuthorizedException();

        }

        model.addAttribute("users", userService.findAll());

        model.addAttribute("isAdmin", isAdmin());

        return "Users/index";

    }


    @PostMapping("/users/profile/upload")

    public void uploadImage(Model model, @RequestParam("image") MultipartFile file, HttpServletResponse response) throws Exception {

        String image = userService.checkUploadedImage(file, getCurrentUser(getAuthentication(), userService));

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "(tried) to upload a new profile picture");

        model.addAttribute("msg", "Uploaded images: " + image);

        response.sendRedirect(URLEncoder.encode("/users/userDetails/" + getCurrentUser(getAuthentication(), userService).getId(), StandardCharsets.UTF_8));

    }


    private boolean hasAdminRights() throws Exception {

        return (getCurrentUser(getAuthentication(), userService).getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));

    }


    @PostMapping("/users/profile/upload/{url}")

    public void uploadRemoteImage(@PathVariable(name = "url") String url, HttpServletResponse response) throws Exception {

        userService.downloadRemoteImage(getCurrentUser(getAuthentication(), userService), url);

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "(tried) to upload a new remote profile picture");

        response.sendRedirect("/users/userDetails/" + getCurrentUser(getAuthentication(), userService).getId());

    }


    @GetMapping("/users/export/{id}")

    public void exportUser(@PathVariable(name = "id") Long id, HttpServletResponse response) throws Exception {

        Optional<User> userOptional = userService.findOneById(id);

        if (userOptional.isEmpty()) {

            l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "logged userid tried to export users, but user wasn't found " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotFoundException();

        }

        if (!hasAdminRights()) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to export users, but are missing admin rights", getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotAuthorizedException();

        }

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "exported user " + id);

        csvExportService.exportUser(List.of(userOptional.get()), response);

    }


    @GetMapping("/users/export")

    public void exportUsers(HttpServletResponse response) throws Exception {

        if (!hasAdminRights()) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to export users, but are missing admin rights", getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotAuthorizedException();

        }

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "exported users " + getCurrentUser(getAuthentication(), userService).getId());


        xmlExportService.exportUsers(userService.findAll());

    }


    @PostMapping("/users/import")

    public void importUsers() throws Exception {

        if (!hasAdminRights()) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to import users, but are missing admin rights", getCurrentUser(getAuthentication(), userService).getId());

            throw new UserNotAuthorizedException();

        }

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "imported users " + getCurrentUser(getAuthentication(), userService).getId());


        xmlExportService.importUsers();

    }


    @PostMapping("/users/profile/changepassword")

    public void changePassword(ChangePasswordRequest request, HttpServletResponse response) throws Exception {

        if (request.getConfirmNewPassword().equals(request.getNewPassword())) {

            User user = getCurrentUser(getAuthentication(), userService);

            if (!userService.validatePasswordComplexity(request.getNewPassword())) {

                l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to change their password, but it was too weak", getCurrentUser(getAuthentication(), userService).getId());

                throw new Exception("Password not complex enough - at least 10 characters required");

            }

            l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "changed their password");

            userService.changePassword(user, request.getNewPassword());

            response.sendRedirect("/users/profile/" + user.getId());

        }

    }


    @GetMapping("/users/settings/import")

    public void importUserSettings(@CookieValue(value = "userSettings") String userSettings) throws IOException {

        userService.importUserSettings(userSettings, getCurrentUser(getAuthentication(), userService));

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "imported user settings");

    }


    @PostMapping("/users/settings/export")

    public void exportUserSettings(HttpServletResponse response) {

        String cookie = userService.exportUserSettings(getCurrentUser(getAuthentication(), userService));

        response.addCookie(new jakarta.servlet.http.Cookie("userSettings", cookie));

        l(logger, Severity.DEBUG, LogType.AUDIT_LOG, "exported user settings");

    }

}
