package by.shakhau.hotel.controller.response;

public record ExceptionResponse(
        String timestamp, String path, Integer status, String error, String requestId) { }
