package com.example.authApp.web;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.authApp.security.InvalidJwtAuthException;
import com.example.authApp.web.RestExceptionHandler;
//import com.example.authApp.web.VehicleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
	
	Logger log=LoggerFactory.getLogger(RestExceptionHandler.class);

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = {InvalidJwtAuthException.class})
    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return status(UNAUTHORIZED).build();
    }
}