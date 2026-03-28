package ru.kpfu.springkr.dto;

import java.time.Instant;

public record NoteResponseDto(
        Long id,
        String title,
        String content,
        Instant createdAt
) {}
