package ru.kpfu.springkr.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kpfu.springkr.dto.ExceptionDto;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoteNotFoundException.class)
    ExceptionDto handleNoteNotFoundException(NoteNotFoundException ex) {
        log.warn(ex.getMessage());
        return ExceptionDto.of(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionDto handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ExceptionDto.validationErrors(errors);
    }

    @ExceptionHandler(TitleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionDto handleTitleAlreadyExistsException(TitleAlreadyExistsException ex) {
        return ExceptionDto.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmptyParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionDto handleEmptyParameterException(EmptyParameterException ex) {
        return ExceptionDto.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionDto handleException(Exception ex) {
        log.warn(ex.toString());
        return ExceptionDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
