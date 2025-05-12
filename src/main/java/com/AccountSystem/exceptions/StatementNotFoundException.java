

package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "statement not found")

public class StatementNotFoundException extends RuntimeException {

}
