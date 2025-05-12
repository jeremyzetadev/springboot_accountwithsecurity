package com.vikingbank.services;



import com.vikingbank.entities.Account;

import com.vikingbank.entities.Invoice;

import com.vikingbank.entities.InvoiceCreationRequest;

import com.vikingbank.exceptions.AccountNotFoundException;

import com.vikingbank.repositories.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;

import java.time.LocalDate;

import java.time.ZoneId;

import java.util.Date;

import java.util.List;

import java.util.Optional;


@Service()


public class InvoiceServicempl implements InvoiceService {

    @Autowired

    private InvoiceRepository invoiceRepository;

    @Autowired

    private AccountService accountService;


    @Override

    public List<Invoice> findSentInvoicesByAccount(Account account) {

        return invoiceRepository.findBySellerAccount(account);

    }


    @Override

    public List<Invoice> findReceivedInvoicesByAccount(Account account) {

        return invoiceRepository.findInvoicesByBuyerAccount(account);

    }


    @Override

    public Optional<Invoice> findOneById(Long id) {

        return invoiceRepository.findOneById(id);

    }


    @Override

    public Invoice save(Invoice invoice) {

        return invoiceRepository.save(invoice);

    }


    @Override

    public void create(InvoiceCreationRequest invoiceCreationRequest, Account ownerAccount) {


        Optional<Account> optionalAccount = accountService.findOneByAccountNumber(invoiceCreationRequest.getBuyerNumber());

        if (optionalAccount.isEmpty()) {

            throw new AccountNotFoundException();

        }

        Invoice invoice = new Invoice(BigDecimal.valueOf(invoiceCreationRequest.getAmount()), optionalAccount.get(), ownerAccount, invoiceCreationRequest.getDescription(), Date.from(LocalDate.parse(invoiceCreationRequest.getDueDate()).atStartOfDay(ZoneId.systemDefault()).toInstant()), false);

        save(invoice);

    }

}
