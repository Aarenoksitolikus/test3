package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteRequestDto {
    @NotBlank(message = "Название обязательно ")
    private String title;
    private String content;
}