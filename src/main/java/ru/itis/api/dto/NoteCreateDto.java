package ru.itis.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteCreateDto {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 150, message = "в title должно быть от 1 до 150 символов")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 5, message = "в content должно быть не менее 5 символов")
    private String content;
}
