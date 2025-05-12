package com.vikingbank.services;


import com.opencsv.exceptions.CsvDataTypeMismatchException;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import com.vikingbank.entities.Transaction;

import com.vikingbank.entities.User;

import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

import java.util.List;


public interface CSVExportService {


    void exportStatement(List<Transaction> transactions, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;


    void exportUser(List<User> users, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

}
