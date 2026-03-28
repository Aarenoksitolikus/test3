package com.example.kr.kr.api;

import com.example.kr.kr.dto.NoteDto;
import com.example.kr.kr.persistence.entity.NoteEntity;
import com.example.kr.kr.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteRestController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<NoteEntity>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/notes/{note-id}")
    public ResponseEntity<NoteEntity> getNoteById(@PathVariable("note-id") Long noteId) {
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteEntity> addNote(@RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(noteService.addNote(noteDto));
    }

    @PutMapping("/notes/{note-id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable("note-id") Long noteId, @RequestBody NoteDto note) {
        return ResponseEntity.ok(noteService.updateNote(noteId, note));
    }

    @DeleteMapping("/notes/{note-id}")
    public ResponseEntity<?> deleteNote(@PathVariable("note-id") Long noteId) {
        try {
            noteService.deleteNote(noteId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
