package ru.itis.fisd_cw.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.fisd_cw.data.dto.NoteDto;
import ru.itis.fisd_cw.data.entity.NoteEntity;
import ru.itis.fisd_cw.service.NoteService;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }


    @PostMapping
    public ResponseEntity<NoteEntity> create(@Valid @RequestBody NoteDto dto) {
        NoteEntity noteEntity = noteService.createNote(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(noteEntity);
    }
}
