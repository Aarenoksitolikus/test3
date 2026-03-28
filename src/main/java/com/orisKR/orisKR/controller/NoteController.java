package com.orisKR.orisKR.controller;

import com.orisKR.orisKR.dto.NoteCreateDto;
import com.orisKR.orisKR.dto.NoteResponseDto;
import com.orisKR.orisKR.dto.NoteUpdateDto;
import com.orisKR.orisKR.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<NoteResponseDto>> getAllNotes(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoteResponseDto> notes = noteService.getAllNotes(pageable);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        NoteResponseDto note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<NoteResponseDto>> searchByTitle(@RequestParam String q) {
        List<NoteResponseDto> notes = noteService.searchByTitle(q);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search/content")
    public ResponseEntity<List<NoteResponseDto>> searchByContent(@RequestParam String q) {
        List<NoteResponseDto> notes = noteService.searchByContent(q);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody NoteCreateDto dto) {
        NoteResponseDto created = noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id, @Valid @RequestBody NoteUpdateDto dto) {
        NoteResponseDto updated = noteService.updateNote(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}