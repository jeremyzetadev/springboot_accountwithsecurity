package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "file size too large")

public class FileSizeTooLargeException extends RuntimeException {

}
