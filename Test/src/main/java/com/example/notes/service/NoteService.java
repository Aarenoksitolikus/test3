package com.example.notes.service;

import com.example.notes.dto.NoteRequestDto;
import com.example.notes.dto.NoteResponseDto;
import com.example.notes.entity.NoteEntity;
import com.example.notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    public final NoteRepository repository;


    public Page<NoteResponseDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    public NoteResponseDto getById(Long id) {
        NoteEntity note = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заметка не найдена"));
        return toDto(note);
    }


    public NoteResponseDto create(NoteRequestDto dto) {
        NoteEntity note = new NoteEntity();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());

        return toDto(repository.save(note));

    }

    public NoteResponseDto update(Long id, NoteRequestDto dto) {
        NoteEntity note = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заметка не найдена"));
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return toDto(repository.save(note));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<NoteResponseDto> searchByTitle(String query, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(query, pageable)
                .map(this::toDto);
    }

    public Page<NoteResponseDto> searchByContent(String quary, Pageable pageable) {
        return repository.findByContentContainingIgnoreCase(quary, pageable)
                .map(this::toDto);
    }

    private NoteResponseDto toDto(NoteEntity entity) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

}