package com.example.KRtest3.service;

import com.example.KRtest3.dto.NoteRequest;
import com.example.KRtest3.dto.NoteResponse;
import ccom.example.KRtest3.entity.Note;
import com.example.KRtest3.exception.NoteNotFoundException;
import com.example.KRtest3.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    private NoteResponse mapToResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    @Transactional
    public NoteResponse create(NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note saved = noteRepository.save(note);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> findAll() {
        return noteRepository.findAllByDeletedAtIsNull().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NoteResponse findById(Long id) {
        Note note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        return mapToResponse(note);
    }

    @Transactional
    public NoteResponse update(Long id, NoteRequest request) {
        Note note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return mapToResponse(note);
    }

    @Transactional
    public void delete(Long id) {
        Note note = noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        note.softDelete();
        noteRepository.save(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> search(String keyword) {
        return noteRepository.searchByKeyword(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> searchByTitle(String titlePart) {
        return noteRepository.findByTitleContainingIgnoreCase(titlePart).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}