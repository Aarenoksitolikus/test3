package org.example.test_maksik.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
