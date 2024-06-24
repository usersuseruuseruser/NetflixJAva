package com.example.exceptionHandling;

import com.example.infrastructure.ExceptionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex);

        ExceptionDetails details = new ExceptionDetails();
        details.message = ex.getMessage();
        details.code = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

}
