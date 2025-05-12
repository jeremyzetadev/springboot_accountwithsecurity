package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "illegal transfer")

public class IllegalTransferException extends RuntimeException {

}
