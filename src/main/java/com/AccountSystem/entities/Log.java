package com.vikingbank.entities;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;

import javax.persistence.Id;

import java.util.Date;


@Entity

public class Log {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private Date timestamp;

    private Severity level;


    private String message;

    private Long userId;


    private LogType logType;


    public Log() {

    }


    public Log(Date timestamp, Severity level, String message, Long userId, LogType logType) {

        this.timestamp = timestamp;

        this.level = level;

        this.message = message;

        this.userId = userId;

        this.logType = logType;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public Date getTimestamp() {

        return timestamp;

    }


    private void setTimestamp(Date timestamp) {

        this.timestamp = timestamp;

    }


    public Severity getLevel() {

        return level;

    }


    private void setLevel(Severity level) {

        this.level = level;

    }


    public String getMessage() {

        return message;

    }


    private void setMessage(String message) {

        this.message = message;

    }


    public Long getUserId() {

        return userId;

    }


    private void setUserId(Long userId) {

        this.userId = userId;

    }


    public LogType getLogType() {

        return logType;

    }


    private void setLogType(LogType logType) {

        this.logType = logType;

    }


}
