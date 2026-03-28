package org.example.cr.api.controller;

import org.example.cr.api.dto.ApiErrorResponseDto;
import org.example.cr.application.exception.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<ApiErrorResponseDto> handleNotExistException(NotExistException ex) {
        if (ex.getApiErrorResponseDto().getCode().equals("404")){
            return new ResponseEntity<>(ex.getApiErrorResponseDto(), HttpStatus.NOT_FOUND);
        }

        else if (ex.getApiErrorResponseDto().getCode().equals("204")){
             return new ResponseEntity<>(ex.getApiErrorResponseDto(), HttpStatus.NO_CONTENT);
        }

        else if (ex.getApiErrorResponseDto().getCode().equals("201")){
             return new ResponseEntity<>(ex.getApiErrorResponseDto(), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(ex.getApiErrorResponseDto(), HttpStatus.BAD_REQUEST);
    }
}
