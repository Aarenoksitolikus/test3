package com.example.demo.service;

import com.example.demo.dto.NoteRequestDto;
import com.example.demo.dto.NoteResponseDto;
import com.example.demo.entity.NoteEntity;
import com.example.demo.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteService {
    
    private final NoteRepository noteRepository;

    private NoteResponseDto convertToDto(NoteEntity entity) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    @Transactional
    public NoteResponseDto create(NoteRequestDto request) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        NoteEntity saved = noteRepository.save(entity);
        return convertToDto(saved);
    }

    public NoteResponseDto getById(Long id) {
        NoteEntity entity = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        
        // проверяем не удалена ли
        if (entity.getDeletedAt() != null) {
            throw new RuntimeException("Note is deleted");
        }
        
        return convertToDto(entity);
    }

    public Page<NoteResponseDto> getAll(Pageable pageable) {
        return noteRepository.findAllActive(pageable)
            .map(this::convertToDto);
    }

    @Transactional
    public NoteResponseDto update(Long id, NoteRequestDto request) {
        NoteEntity entity = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
        
        if (entity.getDeletedAt() != null) {
            throw new RuntimeException("Cannot update deleted note");
        }
        
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setUpdatedAt(LocalDateTime.now());
        
        return convertToDto(noteRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        NoteEntity entity = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
        
        entity.setDeletedAt(LocalDateTime.now());
        noteRepository.save(entity);
    }

    public Page<NoteResponseDto> searchByTitle(String title, Pageable pageable) {
        return noteRepository.searchByTitle(title, pageable)
            .map(this::convertToDto);
    }
    
    // поиск по содержанию
    public Page<NoteResponseDto> searchByContent(String content, Pageable pageable) {
        return noteRepository.searchByContent(content, pageable)
            .map(this::convertToDto);
    }
}