package com.github.felipovski.pokeservice.control.exception.handler;

import java.time.LocalDateTime;

public record ApiErrorResponse(
    String guid,
    String errorCode,
    String message,
    Integer statusCode,
    String statusName,
    String path,
    String method,
    LocalDateTime timestamp)
{}