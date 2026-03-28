package ru.itis.fisd_cw.service;


import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.fisd_cw.entity.NoteEntity;
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

    public void createNote(String title, String content) {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Title can not be blank -_O");
        }

        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Content can not be blank O_-");
        }

        NoteEntity noteEntity = NoteEntity.builder()
                .title(title)
                .content(content)
                .build();

        noteRepository.save(noteEntity);
    }

    public List<NoteEntity> getAll() {
        List<NoteEntity> all = noteRepository.findAll();

        return all;
    }
}
