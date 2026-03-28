package ru.itis.fisd_cw.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteDto {
    @NotBlank(message = "Title can not be blank -_O")
    @Size(max = 32, message = "Title max length is 32 bro...")
    private String title;


    @NotBlank(message = "Content can not be blank -_O")
    @Size(max = 128, message = "Content max length is 128 bro...")
    private String content;
}
