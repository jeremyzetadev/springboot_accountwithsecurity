

package com.vikingbank.exceptions;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "profile picture not found")

public class ProfilePictureNotFoundException extends RuntimeException {

}
