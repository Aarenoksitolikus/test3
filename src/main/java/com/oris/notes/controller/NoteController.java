package com.oris.notes.controller;

import com.oris.notes.dto.NoteRequestDto;
import com.oris.notes.dto.NoteResponseDto;
import com.oris.notes.service.NoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@Validated
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(noteService.getAll(pageable));
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoteResponseDto>> searchByTitle(
            @RequestParam @NotBlank(message = "Параметр query не должен быть пустым") String query,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(noteService.searchByTitle(query, pageable));
    }

    @GetMapping("/search/content")
    public ResponseEntity<Page<NoteResponseDto>> searchByContent(
            @RequestParam @NotBlank(message = "Параметр query не должен быть пустым") String query,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(noteService.searchByContent(query, pageable));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto requestDto
    ) {
        return ResponseEntity.ok(noteService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
