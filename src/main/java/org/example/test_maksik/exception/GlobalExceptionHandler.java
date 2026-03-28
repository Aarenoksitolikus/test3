package org.example.test_maksik.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR OF VALIDATION");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOther(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bruh");
    }
}
