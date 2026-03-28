package ru.itis.kr.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.itis.kr.api.schema.response.Response;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NoteDto extends Response {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
