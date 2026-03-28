package com.orisKR.orisKR.service;

import com.orisKR.orisKR.dto.NoteCreateDto;
import com.orisKR.orisKR.dto.NoteResponseDto;
import com.orisKR.orisKR.dto.NoteUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {
    Page<NoteResponseDto> getAllNotes(Pageable pageable);
    NoteResponseDto getNoteById(Long id);
    NoteResponseDto createNote(NoteCreateDto dto);
    NoteResponseDto updateNote(Long id, NoteUpdateDto dto);
    void deleteNote(Long id);
    List<NoteResponseDto> searchByTitle(String title);
    List<NoteResponseDto> searchByContent(String content);
}