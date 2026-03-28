package ru.itis.kr.api.schema.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateNoteRequest {
    private String title;
    private String content;
}
