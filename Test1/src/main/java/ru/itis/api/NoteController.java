package ru.itis.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.persistence.entity.NoteEntity;
import ru.itis.service.NoteService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    @GetMapping("/notes")
    public Page<NoteEntity> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page,size);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<NoteEntity> getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @GetMapping("/search?q={title}")
    public Page<NoteEntity> getNotesByTitle(@PathVariable("title") String title) {
        return service.findNotesByTitle(title);
    }

    @GetMapping("/search?content={content}")
    public Page<NoteEntity> getNotesByContent(@PathVariable("content") String content) {
        return service.findNotesByContent(content);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNote(@RequestParam String title, @RequestParam String content) {
        service.saveNote(title, content, LocalDate.now());
    }

    @PutMapping
    public void updateNote(@RequestParam UUID id, @RequestParam String title, @RequestParam String content) {
        service.updateNote(id, title, content, LocalDate.now());
    }

    @DeleteMapping
    public void deleteNote(@RequestParam UUID id) {
        service.deleteNote(id, LocalDate.now());
    }
}
