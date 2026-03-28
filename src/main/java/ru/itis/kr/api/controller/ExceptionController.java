package ru.itis.kr.api.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.itis.kr.api.schema.response.ErrorResponse;
import ru.itis.kr.api.schema.response.ValidationViolationResponse;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleException() {
        ErrorResponse response = ErrorResponse.builder()
                .message("Неправильно задан запрос")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ValidationViolationResponse> handleValidationViolations(ConstraintViolationException e) {
        ValidationViolationResponse response = ValidationViolationResponse.builder()
                .message("Нарушение требований к параметрам")
                .violations(e.getConstraintViolations().stream()
                        .map(violation -> violation.getPropertyPath() + ": " +
                                violation.getMessageTemplate()).toArray(String[]::new))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
