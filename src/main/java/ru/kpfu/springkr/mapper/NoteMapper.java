package ru.kpfu.springkr.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.springkr.dto.NoteCreateDto;
import ru.kpfu.springkr.dto.NoteResponseDto;
import ru.kpfu.springkr.model.NoteEntity;

@Component
public class NoteMapper {

    public NoteEntity fromDto(NoteCreateDto dto) {
        return NoteEntity.builder()
                .title(dto.title())
                .content(dto.content())
                .build();
    }

    public NoteResponseDto toDto(NoteEntity note) {
        return new NoteResponseDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt()
        );
    }

}
