package com.example.kr.kr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteDto {
    @NotBlank
    @NotNull
    @Size(min = 1, max = 50)
    private String title;
    @Size(max = 1000)
    private String content;
}