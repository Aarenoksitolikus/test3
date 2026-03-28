package ru.itis.fisd_cw.service;


import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.fisd_cw.data.dto.NoteDto;
import ru.itis.fisd_cw.data.entity.NoteEntity;
import ru.itis.fisd_cw.repository.NoteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;


    public NoteEntity getByTitle(String title) {
        return (NoteEntity) noteRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(title);
    }

    public NoteEntity getByContent(String content) {
        return (NoteEntity) noteRepository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(content);
    }

    public NoteEntity createNote(NoteDto dto) {
        NoteEntity noteEntity = NoteEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return noteRepository.save(noteEntity);
    }


}
