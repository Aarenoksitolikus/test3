package com.example.notes.service.impl;

import com.example.notes.dto.NoteCreateDto;
import com.example.notes.dto.NotePageDto;
import com.example.notes.dto.NoteResponseDto;
import com.example.notes.dto.NoteUpdateDto;
import com.example.notes.entity.NoteEntity;
import com.example.notes.exception.NoteNotFoundException;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Transactional(readOnly = true)
    public NotePageDto getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NoteEntity> notePage = noteRepository.findAllByDeletedAtIsNull(pageable);
        
        List<NoteResponseDto> content = notePage.getContent().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        
        return new NotePageDto(
                notePage.getTotalElements(),
                notePage.getTotalPages(),
                content
        );
    }

    @Override
    @Transactional(readOnly = true)
    public NoteResponseDto getById(Long id) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + id + " not found"));
        return toResponseDto(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> getDeleted() {
        return noteRepository.findAllByDeletedAtIsNotNull().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> searchByTitle(String title) {
        return noteRepository.findAllByTitleContainingIgnoreCaseAndDeletedAtIsNull(title).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NoteResponseDto create(NoteCreateDto noteCreateDto) {
        NoteEntity note = new NoteEntity();
        note.setTitle(noteCreateDto.getTitle());
        note.setContent(noteCreateDto.getContent());
        
        NoteEntity savedNote = noteRepository.save(note);
        return toResponseDto(savedNote);
    }

    @Override
    @Transactional
    public NoteResponseDto update(Long id, NoteUpdateDto noteUpdateDto) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + id + " not found"));
        
        note.setTitle(noteUpdateDto.getTitle());
        note.setContent(noteUpdateDto.getContent());
        
        NoteEntity updatedNote = noteRepository.save(note);
        return toResponseDto(updatedNote);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        NoteEntity note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with id " + id + " not found"));
        
        note.setDeletedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    private NoteResponseDto toResponseDto(NoteEntity note) {
        return new NoteResponseDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getDeletedAt()
        );
    }
}
