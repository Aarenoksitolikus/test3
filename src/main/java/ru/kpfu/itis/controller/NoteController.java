package ru.kpfu.itis.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.NoteDto;
import ru.kpfu.itis.service.NoteService;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {
        NoteDto created = noteService.createNote(noteDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {
        NoteDto note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<Page<NoteDto>> getAllNotes(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoteDto> notes = noteService.getAllNotes(pageable);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoteDto>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoteDto> notes = noteService.searchByTitle(title, pageable);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search/content")
    public ResponseEntity<Page<NoteDto>> searchByContent(
            @RequestParam String content,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoteDto> notes = noteService.searchByContent(content, pageable);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteDto noteDto) {
        NoteDto updated = noteService.updateNote(id, noteDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}