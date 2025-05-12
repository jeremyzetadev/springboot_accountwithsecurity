

package com.vikingbank.services;


import com.vikingbank.entities.Log;

import com.vikingbank.entities.LogType;

import com.vikingbank.entities.Severity;

import org.springframework.stereotype.Service;


import java.util.List;


@Service

public interface LogService {

    List<Log> getLogs();


    void log(Severity severity, String message, Long userId, LogType logType);

}
