package by.shakhau.hotel.controller;

import by.shakhau.hotel.controller.exception.EntityNotFoundException;
import by.shakhau.hotel.controller.exception.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return new ExceptionResponse(
                Instant.now().toString(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI());
    }
}
