package com.example.kr.kr.service;

import com.example.kr.kr.dto.NoteDto;
import com.example.kr.kr.persistence.entity.NoteEntity;

import java.util.List;

public interface NoteService {
    List<NoteEntity> getAllNotes();

    NoteDto updateNote(Long noteId, NoteDto note);

    void deleteNote(Long noteId);

    NoteEntity getNoteById(Long noteId);

    NoteEntity addNote(NoteDto noteDto);
}
