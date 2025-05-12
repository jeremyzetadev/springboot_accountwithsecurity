package com.vikingbank.services;


import com.vikingbank.entities.Log;

import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import com.vikingbank.repositories.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Date;

import java.util.List;


@Service()


public class LogServiceImpl implements LogService {

    @Autowired

    private LogRepository logRepository;


    @Override

    public List<Log> getLogs() {

        return logRepository.findAll();

    }


    @Override

    public void log(Severity severity, String message, Long userId, LogType logType) {

        logRepository.save(new Log(new Date(), Severity.INFO, message, userId, logType));

    }

}
