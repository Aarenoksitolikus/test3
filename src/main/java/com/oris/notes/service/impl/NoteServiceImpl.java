package com.oris.notes.service.impl;

import com.oris.notes.dto.NoteRequestDto;
import com.oris.notes.dto.NoteResponseDto;
import com.oris.notes.entity.NoteEntity;
import com.oris.notes.exception.NoteNotFoundException;
import com.oris.notes.repository.NoteRepository;
import com.oris.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    @Transactional
    public NoteResponseDto create(NoteRequestDto requestDto) {
        NoteEntity entity = NoteEntity.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        NoteEntity saved = noteRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public NoteResponseDto update(Long id, NoteRequestDto requestDto) {
        NoteEntity entity = getActiveNoteOrThrow(id);
        entity.setTitle(requestDto.getTitle());
        entity.setContent(requestDto.getContent());

        NoteEntity updated = noteRepository.save(entity);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        NoteEntity entity = getActiveNoteOrThrow(id);
        entity.setDeletedAt(LocalDateTime.now());
        noteRepository.save(entity);
    }

    @Override
    public NoteResponseDto getById(Long id) {
        return toResponse(getActiveNoteOrThrow(id));
    }

    @Override
    public Page<NoteResponseDto> getAll(Pageable pageable) {
        return noteRepository.findAllByDeletedAtIsNull(pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<NoteResponseDto> searchByTitle(String query, Pageable pageable) {
        return noteRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(query, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<NoteResponseDto> searchByContent(String query, Pageable pageable) {
        return noteRepository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(query, pageable)
                .map(this::toResponse);
    }

    private NoteEntity getActiveNoteOrThrow(Long id) {
        return noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    private NoteResponseDto toResponse(NoteEntity entity) {
        return NoteResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

