

package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "invalid user settings")

public class InvalidUserSettingsException extends RuntimeException {

    public InvalidUserSettingsException(String message, Exception e) {

        super(message, e);

    }


    public InvalidUserSettingsException() {


    }

}
