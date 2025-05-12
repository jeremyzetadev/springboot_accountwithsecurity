

package com.vikingbank.repositories;


import com.vikingbank.entities.Account;

import com.vikingbank.entities.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;

import java.util.Optional;


@Repository

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findBySellerAccount(Account account);


    Optional<Invoice> findOneById(Long id);


    List<Invoice> findInvoicesByBuyerAccount(Account account);

}
