package com.example.kr.kr.service;

import com.example.kr.kr.dto.NoteDto;
import com.example.kr.kr.persistence.entity.NoteEntity;
import com.example.kr.kr.persistence.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public NoteDto updateNote(Long noteId, NoteDto note) {
        NoteEntity noteForUpdate = noteRepository.findById(noteId)
                .orElseThrow(IllegalArgumentException::new);
        noteForUpdate.setTitle(note.getTitle());
        noteForUpdate.setContent(note.getContent());
        noteForUpdate.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(noteForUpdate);
        return note;
    }

    public void deleteNote(Long noteId) {
        NoteEntity noteForDelete = noteRepository.findById(noteId)
                .orElseThrow(IllegalArgumentException::new);
        noteForDelete.setDeletedAt(LocalDateTime.now());
        noteRepository.save(noteForDelete);

    }

    @Override
    public NoteEntity getNoteById(Long noteId) {
        return noteRepository.getById(noteId);
    }

    @Override
    public NoteEntity addNote(NoteDto noteDto) {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteDto.getTitle());
        noteEntity.setContent(noteDto.getContent());
        noteEntity.setCreatedAt(LocalDateTime.now());
        noteRepository.save(noteEntity);
        return noteEntity;
    }

}
