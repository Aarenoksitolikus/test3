package ru.itis.fisd_cw.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.fisd_cw.data.dto.NoteDto;
import ru.itis.fisd_cw.data.entity.NoteEntity;
import ru.itis.fisd_cw.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;


    @GetMapping
    public ResponseEntity<Page<NoteEntity>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(noteService.getAllNotes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @GetMapping("/title")
    public ResponseEntity<List<NoteEntity>> getByTitle(@RequestParam String title) {
        return ResponseEntity.ok(noteService.getByTitle(title));
    }

    @GetMapping("/content")
    public ResponseEntity<List<NoteEntity>> getByContent(@RequestParam String content) {
        return ResponseEntity.ok(noteService.getByContent(content));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteEntity> update(@PathVariable Long id, @RequestBody NoteDto dto) {
        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<NoteEntity> create(@Valid @RequestBody NoteDto dto) {
        NoteEntity noteEntity = noteService.createNote(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(noteEntity);
    }
}
