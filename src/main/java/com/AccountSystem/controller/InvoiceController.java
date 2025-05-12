package com.vikingbank.controller;


import com.vikingbank.entities.*;

import com.vikingbank.exceptions.InvoiceNotFoundException;

import com.vikingbank.services.AccountService;

import com.vikingbank.services.InvoiceService;

import com.vikingbank.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;

import java.util.List;

import java.util.Optional;


@Controller

public class InvoiceController extends BaseController {

    @Autowired

    @Qualifier("invoiceLogger")

    private Logger logger;


    @Autowired

    private InvoiceService invoiceService;

    @Autowired

    private AccountService accountService;

    @Autowired

    private UserService userService;


    @GetMapping("/invoices")

    public String index(Model model) throws Exception {


        User user = getCurrentUser(getAuthentication(), userService);

        List<Invoice> sentInvoices = new ArrayList<>();

        List<Invoice> receivedInvoices = new ArrayList<>();


        user.getAccounts().forEach(account -> {

            sentInvoices.addAll(invoiceService.findSentInvoicesByAccount(account));

            receivedInvoices.addAll(invoiceService.findReceivedInvoicesByAccount(account));

        });


        if (sentInvoices.size() != 0) {

            model.addAttribute("sentinvoices", sentInvoices);

        } else {

            model.addAttribute("noSentInvoices", true);

        }


        if (receivedInvoices.size() != 0) {

            model.addAttribute("receivedInvoices", receivedInvoices);

        } else {

            model.addAttribute("noReceievedInvoices", true);

        }

        model.addAttribute("isAdmin", isAdmin());


        return "Invoice/index";

    }


    @GetMapping("/invoices/{id}")

    public String view(@PathVariable(name = "id") Long id, Model model) throws Exception {

        User user = getCurrentUser(getAuthentication(), userService);

        Optional<Invoice> invoiceOptional = invoiceService.findOneById(id);

        if (invoiceOptional.isEmpty()) {

            l(logger, Severity.WARN, LogType.AUDIT_LOG, "logged userid tried to view transaction details of account " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new InvoiceNotFoundException();

        }

        Invoice invoice = invoiceOptional.get();

        model.addAttribute("isBuyer", user.getAccounts().stream().anyMatch(account -> account.getId().equals(invoice.getBuyerAccount().getId())));

        model.addAttribute("isAdmin", isAdmin());

        model.addAttribute("invoice", invoice);

        return "Invoice/view";

    }


    @GetMapping("/invoices/create")

    public String create(Model model) throws Exception {

        model.addAttribute("createinvoicerequest", new InvoiceCreationRequest());

        model.addAttribute("isAdmin", isAdmin());

        return "Invoice/create";

    }


    @PostMapping("/invoices/create")

    public void doCreate(HttpServletResponse response, InvoiceCreationRequest request) throws Exception {

        // get account by user id

        invoiceService.create(request, accountService.findAccountsByUser(getCurrentUser(getAuthentication(), userService)).get(0)); // assume first account for now.

        response.sendRedirect("/invoices");

    }


    @GetMapping("/invoices/{id}/payment")

    public String payInvoiceView(@PathVariable(name = "id") Long id, Model model) throws Exception {

        Invoice invoice = findInvoiceById(id);

        model.addAttribute("isAdmin", isAdmin());

        model.addAttribute("invoice", invoice);

        return "Invoice/payinvoice";

    }


    @PostMapping("/invoices/{id}/payment")

    public void payInvoice(@PathVariable(name = "id") Long id, HttpServletResponse response) throws Exception {

        Invoice invoice = findInvoiceById(id);

        invoice.updatePaymentStatus();

        invoiceService.save(invoice);

        l(logger, Severity.INFO, LogType.AUDIT_LOG, "paid invoice " + id);

        response.sendRedirect("/invoices");

    }


    private Invoice findInvoiceById(@PathVariable(name = "id") Long id) throws Exception {

        Optional<Invoice> invoiceOptional = invoiceService.findOneById(id);

        if (invoiceOptional.isEmpty()) {

            throw new InvoiceNotFoundException();

        }


        Invoice invoice = invoiceOptional.get();

        if (!userService.validate(invoice.getBuyerAccount().getId(), getCurrentUser(getAuthentication(), userService))) {

            l(logger, Severity.INFO, LogType.AUDIT_LOG, "logged userid tried to view an invoice that doesn't involve them " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new InvoiceNotFoundException();

        }


        return invoice;

    }


    @GetMapping("/invoices/{id}/sendReminder")

    public void sendReminder(Long id, HttpServletResponse response) throws Exception {

        Invoice invoice = findInvoiceById(id);


        // logic

        response.sendRedirect("/locationhere");

    }


    @GetMapping("/invoices/{id}/paymentReceipt")

    public String getPaymentReceipt(@PathVariable(name = "id") Long id, Model model) throws Exception {

        Invoice invoice = findInvoiceById(id);

        if (!userService.validate(invoice.getBuyerAccount().getId(), getCurrentUser(getAuthentication(), userService))) {

            l(logger, Severity.INFO, LogType.AUDIT_LOG, "logged userid tried to view the receipt for an invoice that doesn't involve them " + id, getCurrentUser(getAuthentication(), userService).getId());

            throw new InvoiceNotFoundException();

        }

        model.addAttribute("invoice", invoice);

        model.addAttribute("isAdmin", isAdmin());


        l(logger, Severity.INFO, LogType.AUDIT_LOG, "payment receipt has been viewed " + id);

        return "Invoice/receipt";

    }

}
