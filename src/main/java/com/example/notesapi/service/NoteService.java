package com.example.notesapi.service;

import com.example.notesapi.dto.CreateNoteRequest;
import com.example.notesapi.dto.NoteResponse;
import com.example.notesapi.dto.UpdateNoteRequest;
import com.example.notesapi.entity.Note;
import com.example.notesapi.exception.ResourceNotFoundException;
import com.example.notesapi.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteResponse createNote(CreateNoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note savedNote = noteRepository.save(note);
        return mapToResponse(savedNote);
    }

    public NoteResponse getNoteById(Long id) {
        Note note = findActiveNoteById(id);
        return mapToResponse(note);
    }

    public Page<NoteResponse> getAllNotes(Pageable pageable) {
        return noteRepository.findByDeletedAtIsNull(pageable).map(this::mapToResponse);
    }

    public Page<NoteResponse> searchNotesByTitle(String query, Pageable pageable) {
        return noteRepository.findByTitleContaining(query, pageable).map(this::mapToResponse);
    }

    public Page<NoteResponse> searchNotesByContent(String query, Pageable pageable) {
        return noteRepository.findByContentContaining(query, pageable).map(this::mapToResponse);
    }

    public NoteResponse updateNote(Long id, UpdateNoteRequest request) {
        Note note = findActiveNoteById(id);
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note updatedNote = noteRepository.save(note);
        return mapToResponse(updatedNote);
    }

    public void deleteNote(Long id) {
        Note note = findActiveNoteById(id);
        note.setDeletedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    private Note findActiveNoteById(Long id) {
        return noteRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
    }

    private NoteResponse mapToResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}

