package com.example.notesapi.controller;

import com.example.notesapi.dto.CreateNoteRequest;
import com.example.notesapi.dto.NoteResponse;
import com.example.notesapi.dto.UpdateNoteRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.example.notesapi.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Validated
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse response = noteService.getNoteById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponse>> getAllNotes(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "page must be >= 0") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "size must be >= 1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NoteResponse> response = noteService.getAllNotes(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoteResponse>> searchNotesByTitle(
            @RequestParam @NotBlank(message = "query cannot be blank") String query,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "page must be >= 0") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "size must be >= 1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NoteResponse> response = noteService.searchNotesByTitle(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/content")
    public ResponseEntity<Page<NoteResponse>> searchNotesByContent(
            @RequestParam @NotBlank(message = "query cannot be blank") String query,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "page must be >= 0") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "size must be >= 1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NoteResponse> response = noteService.searchNotesByContent(query, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest request) {
        NoteResponse response = noteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, @Valid @RequestBody UpdateNoteRequest request) {
        NoteResponse response = noteService.updateNote(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}

