package com.oris.notes.service;

import com.oris.notes.dto.NoteRequestDto;
import com.oris.notes.dto.NoteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    NoteResponseDto create(NoteRequestDto requestDto);

    NoteResponseDto update(Long id, NoteRequestDto requestDto);

    void delete(Long id);

    NoteResponseDto getById(Long id);

    Page<NoteResponseDto> getAll(Pageable pageable);

    Page<NoteResponseDto> searchByTitle(String query, Pageable pageable);

    Page<NoteResponseDto> searchByContent(String query, Pageable pageable);
}

