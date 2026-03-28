package org.example.cr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiErrorResponseDto {
    private String description;
    private String code;

}
