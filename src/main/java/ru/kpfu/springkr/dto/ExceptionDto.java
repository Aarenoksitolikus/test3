package ru.kpfu.springkr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionDto(
        Integer statusCode,
        String message,
        Map<String, String> validationErrors
) {

    public static ExceptionDto of(HttpStatus statusCode, String message) {
        return new ExceptionDto(statusCode.value(), message, null);
    }

    public static ExceptionDto validationErrors(Map<String, String> errors) {
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                "Provided arguments are invalid",
                errors
        );
    }

}
