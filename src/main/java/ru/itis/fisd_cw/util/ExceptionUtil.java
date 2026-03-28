package ru.itis.fisd_cw.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.fisd_cw.util.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionUtil {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("msg", exception.getMessage());


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(IllegalArgumentException exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("msg", exception.getMessage());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("msg", exception.getMessage());


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
}
