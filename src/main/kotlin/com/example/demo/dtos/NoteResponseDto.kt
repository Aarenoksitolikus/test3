package com.example.demo.dtos

data class NoteResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)
