package ru.itis.fisd_cw.service;


import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.fisd_cw.data.dto.NoteDto;
import ru.itis.fisd_cw.data.entity.NoteEntity;
import ru.itis.fisd_cw.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private NoteRepository noteRepository;

    //GET TITLE
    public List<NoteEntity> getByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(title);
    }

    //GET CONTENT
    public List<NoteEntity> getByContent(String content) {
        return noteRepository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(content);
    }

    //CREATE
    public NoteEntity createNote(NoteDto dto) {
        NoteEntity noteEntity = NoteEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return noteRepository.save(noteEntity);
    }

    //GET 1
    public NoteEntity getById(Long id) {
        return noteRepository.findByIdAndDeletedAtIsNull(id);
    }


    //GET ALL
    public Page<NoteEntity> getAllNotes(Pageable pageable) {
        return noteRepository.findAllByDeletedAtIsNull(pageable);
    }

    //UPDATE
    @Transactional
    public NoteEntity updateNote(Long id, NoteDto dto) {
        NoteEntity noteEntity = noteRepository.findByIdAndDeletedAtIsNull(id);

        noteEntity.setTitle(dto.getTitle());
        noteEntity.setContent(dto.getContent());

        return noteRepository.save(noteEntity);
    }

    //DELETE
    @Transactional
    public void deleteNote(Long id) {
        NoteEntity noteEntity = noteRepository.findByIdAndDeletedAtIsNull(id);

        noteEntity.delete();

        noteRepository.save(noteEntity);
    }

}
