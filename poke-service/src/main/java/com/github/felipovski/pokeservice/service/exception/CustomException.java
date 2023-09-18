package com.github.felipovski.pokeservice.service.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException  {

    public String errorCode;
    public String message;
    public HttpStatus httpStatus;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}