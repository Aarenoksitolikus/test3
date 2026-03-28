package com.example.notes.controller;

import com.example.notes.dto.NoteCreateDto;
import com.example.notes.dto.NotePageDto;
import com.example.notes.dto.NoteResponseDto;
import com.example.notes.dto.NoteUpdateDto;
import com.example.notes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<NotePageDto> getAllNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(noteService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<NoteResponseDto>> getDeletedNotes() {
        return ResponseEntity.ok(noteService.getDeleted());
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoteResponseDto>> search(@RequestParam String query) {
        return ResponseEntity.ok(noteService.searchByTitle(query));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteCreateDto noteCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(noteCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteUpdateDto noteUpdateDto) {
        return ResponseEntity.ok(noteService.update(id, noteUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
