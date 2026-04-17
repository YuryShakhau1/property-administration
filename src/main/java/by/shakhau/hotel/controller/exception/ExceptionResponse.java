package by.shakhau.hotel.controller.exception;

public record ExceptionResponse(
        String timestamp, String error, Integer status, String path) { }
