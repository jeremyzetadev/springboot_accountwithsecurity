package com.vikingbank.services;


import com.opencsv.CSVWriter;

import com.opencsv.bean.StatefulBeanToCsv;

import com.opencsv.bean.StatefulBeanToCsvBuilder;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import com.vikingbank.entities.Transaction;

import com.vikingbank.entities.User;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;


import java.io.IOException;

import java.util.List;


@Service

public class CSVExportServiceImpl implements CSVExportService {


    @Override

    public void exportStatement(List<Transaction> transactions, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {


        String filename = "export.csv";


        response.setContentType("text/csv");

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,

                "attachment; filename=\"" + filename + "\"");

        // create a csv writer

        StatefulBeanToCsv<Transaction> writer =

                new StatefulBeanToCsvBuilder<Transaction>

                        (response.getWriter())

                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)

                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)

                        .withOrderedResults(false).build();


        writer.write(transactions);

    }


    public void exportUser(List<User> users, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {


        String filename = "export.csv";


        response.setContentType("text/csv");

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,

                "attachment; filename=\"" + filename + "\"");

        // create a csv writer

        StatefulBeanToCsv<User> writer =

                new StatefulBeanToCsvBuilder<User>

                        (response.getWriter())

                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)

                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)

                        .withOrderedResults(false).build();


        writer.write(users);

    }


}
