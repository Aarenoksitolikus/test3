package com.example.demo.dtos

import jakarta.validation.constraints.NotBlank

data class NoteDto(
    @field:NotBlank(message = "Title cannot be blank")
    val title: String,

    @field:NotBlank(message = "Content cannot be blank")
    val content: String
)
