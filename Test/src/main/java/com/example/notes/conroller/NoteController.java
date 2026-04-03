package com.example.notes.controller;

import com.example.notes.dto.NoteRequestDto;
import com.example.notes.dto.NoteResponseDto;
import com.example.notes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService service;

    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoteResponseDto>> searchByTitle(@RequestParam String q, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.searchByTitle(q, pageable));
    }

    @GetMapping("/search/content")
    public ResponseEntity<Page<NoteResponseDto>> searchByContent(@RequestParam String q, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.searchByContent(q, pageable));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(@PathVariable Long id, @Valid @RequestBody NoteRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}