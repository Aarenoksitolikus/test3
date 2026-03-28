package ru.itis.kr.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.kr.api.schema.request.CreateNoteRequest;
import ru.itis.kr.api.schema.response.ErrorResponse;
import ru.itis.kr.api.schema.response.Response;
import ru.itis.kr.persistence.dto.NoteDto;
import ru.itis.kr.service.NoteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping()
    public ResponseEntity<? super Response> getAll(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<NoteDto> note = noteService.getAll(page, size);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<? super Response> findById(@PathVariable long id) {
        Optional<NoteDto> note = noteService.findById(id);
        if (note.isEmpty()) {
            return new ResponseEntity<>(
                    new ErrorResponse("Заметка с таким ID не найдена"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<? super Response> searchByTitle(@RequestParam String query) {
        List<NoteDto> notes = noteService.searchByTitle(query);
        if (notes.isEmpty()) {
            return new ResponseEntity<>(
                    new ErrorResponse("Ничего не найдено по этому запросу"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<? super Response> searchByContent(@RequestParam String query) {
        List<NoteDto> notes = noteService.searchByContent(query);
        if (notes.isEmpty()) {
            return new ResponseEntity<>(
                    new ErrorResponse("Ничего не найдено по этому запросу"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<? super Response> create(@RequestBody CreateNoteRequest request) {
        NoteDto note = noteService.create(request.getTitle(), request.getContent()).orElse(null);
        if (note == null) {
            return new ResponseEntity<>(
                    new ErrorResponse("Заметка с таким заголовком уже существует"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }
}
