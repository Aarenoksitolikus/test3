package com.example.notes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteRequestDto {
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;

    @NotBlank(message = "Текст не может быть пустым")
    private String content;
}