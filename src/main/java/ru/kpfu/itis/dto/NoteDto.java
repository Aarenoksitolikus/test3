package ru.kpfu.itis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteDto {

    private Long id;

    @NotBlank(message = "Тайтл не должен быть пустым")
    @Size(min = 1, max = 200, message = "Title должен содержать 1 and 200 символов")

    private String title;
    private String createdAt;
    private String updatedAt;

    private String content;

}
