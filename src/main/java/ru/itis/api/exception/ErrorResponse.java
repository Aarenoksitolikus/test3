package ru.itis.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this(status, message, null, timestamp);
    }
}
