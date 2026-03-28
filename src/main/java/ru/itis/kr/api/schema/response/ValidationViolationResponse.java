package ru.itis.kr.api.schema.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ValidationViolationResponse {
    private String message;
    private String[] violations;
}
