package com.vikingbank.services;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Invoice;

import com.vikingbank.entities.InvoiceCreationRequest;

import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;


@Service

public interface InvoiceService {

    List<Invoice> findSentInvoicesByAccount(Account account);


    List<Invoice> findReceivedInvoicesByAccount(Account account);


    Optional<Invoice> findOneById(Long id);


    Invoice save(Invoice invoice);


    void create(InvoiceCreationRequest invoiceCreationRequest, Account account);

}
