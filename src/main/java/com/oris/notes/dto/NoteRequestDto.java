package com.oris.notes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequestDto {

    @NotBlank(message = "Заголовок не должен быть пустым")
    @Size(max = 255, message = "Заголовок должен быть не длиннее 255 символов")
    private String title;

    @NotBlank(message = "Содержимое не должно быть пустым")
    private String content;
}
