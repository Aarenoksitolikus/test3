package com.example.notes.service;

import com.example.notes.dto.NoteCreateDto;
import com.example.notes.dto.NotePageDto;
import com.example.notes.dto.NoteResponseDto;
import com.example.notes.dto.NoteUpdateDto;

import java.util.List;

public interface NoteService {

    NotePageDto getAll(int page, int size);

    NoteResponseDto getById(Long id);

    List<NoteResponseDto> getDeleted();

    List<NoteResponseDto> searchByTitle(String title);

    NoteResponseDto create(NoteCreateDto noteCreateDto);

    NoteResponseDto update(Long id, NoteUpdateDto noteUpdateDto);

    void delete(Long id);
}
