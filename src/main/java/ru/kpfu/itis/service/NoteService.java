package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.NoteDto;
import ru.kpfu.itis.entity.NoteEntity;
import ru.kpfu.itis.exception.NoteNotFoundException;
import ru.kpfu.itis.repository.NoteRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public NoteDto createNote(NoteDto noteDto) {
        NoteEntity note = new NoteEntity();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        NoteEntity saved = noteRepository.save(note);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public NoteDto getNoteById(Long id) {
        NoteEntity note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        if (note.getDeletedAt() != null) {
            throw new NoteNotFoundException("Note was deleted");
        }

        return convertToDto(note);
    }

    @Transactional(readOnly = true)
    public Page<NoteDto> getAllNotes(Pageable pageable) {
        return noteRepository.findAllActive(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public Page<NoteDto> searchByTitle(String title, Pageable pageable) {
        return noteRepository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(title, pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public Page<NoteDto> searchByContent(String content, Pageable pageable) {
        return noteRepository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(content, pageable)
                .map(this::convertToDto);
    }

    @Transactional
    public NoteDto updateNote(Long id, NoteDto noteDto) {
        NoteEntity note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        if (note.getDeletedAt() != null) {
            throw new NoteNotFoundException("Cannot update deleted note");
        }

        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        NoteEntity updated = noteRepository.save(note);
        return convertToDto(updated);
    }

    @Transactional
    public void deleteNote(Long id) {
        NoteEntity note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        if (note.getDeletedAt() != null) {
            throw new NoteNotFoundException("Note already deleted");
        }

        note.setDeletedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    private NoteDto convertToDto(NoteEntity note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());

        if (note.getCreatedAt() != null) {
            dto.setCreatedAt(note.getCreatedAt().format(formatter));
        }

        if (note.getUpdatedAt() != null) {
            dto.setUpdatedAt(note.getUpdatedAt().format(formatter));
        }

        return dto;
    }
}