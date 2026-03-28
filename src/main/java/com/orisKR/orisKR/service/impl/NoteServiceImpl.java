package com.orisKR.orisKR.service.impl;

import com.orisKR.orisKR.dto.NoteCreateDto;
import com.orisKR.orisKR.dto.NoteResponseDto;
import com.orisKR.orisKR.dto.NoteUpdateDto;
import com.orisKR.orisKR.entity.NoteEntity;
import com.orisKR.orisKR.exception.ResourceNotFoundException;
import com.orisKR.orisKR.repository.NoteRepository;
import com.orisKR.orisKR.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Page<NoteResponseDto> getAllNotes(Pageable pageable) {
        return noteRepository.findAllByDeletedAtIsNull(pageable)
                .map(this::toResponseDto);
    }

    @Override
    public NoteResponseDto getNoteById(Long id) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return toResponseDto(note);
    }

    @Override
    @Transactional
    public NoteResponseDto createNote(NoteCreateDto dto) {
        NoteEntity note = NoteEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        NoteEntity saved = noteRepository.save(note);
        return toResponseDto(saved);
    }

    @Override
    @Transactional
    public NoteResponseDto updateNote(Long id, NoteUpdateDto dto) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        NoteEntity updated = noteRepository.save(note);
        return toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        note.setDeletedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    @Override
    public List<NoteResponseDto> searchByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(title)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDto> searchByContent(String content) {
        return noteRepository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(content)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private NoteResponseDto toResponseDto(NoteEntity entity) {
        return NoteResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}