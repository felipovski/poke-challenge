package com.github.felipovski.pokeservice.service.exception;

import org.springframework.http.HttpStatus;

public class IllegalSortingTypeException extends CustomException {

    public IllegalSortingTypeException() {
        this.errorCode = ErrorCode.POKE_ERROR_0001.toString();
        this.message = ErrorCode.POKE_ERROR_0001.getMessage();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
