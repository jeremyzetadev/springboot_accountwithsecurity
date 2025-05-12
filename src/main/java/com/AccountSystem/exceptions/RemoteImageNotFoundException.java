

package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "remote image not found")

public class RemoteImageNotFoundException extends RuntimeException {

}
