package ru.kpfu.springkr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoteCreateDto(

        @Size(min = 1, max = 255)
        @NotBlank
        String title,

        @Size(max = 1024)
        String content

) {}
