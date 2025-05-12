package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "something seems broken")

public class GenericException extends RuntimeException {


    public GenericException() {

    }


    public GenericException(String message, Throwable cause) {

        super(message, cause);

    }

}
