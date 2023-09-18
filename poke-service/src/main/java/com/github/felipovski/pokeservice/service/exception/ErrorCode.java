package com.github.felipovski.pokeservice.service.exception;

public enum ErrorCode {

    POKE_ERROR_0001("Sort type does not exist.");
    private final String message;
    ErrorCode(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}