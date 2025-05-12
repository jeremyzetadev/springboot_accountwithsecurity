package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "invalid remote image")

public class InvalidRemoteImageException extends RuntimeException {

}
