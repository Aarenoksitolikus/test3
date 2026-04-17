package notes.controller;

import notes.dto.NoteRequestDto;
import notes.dto.NoteResponseDto;
import notes.service.NoteService;
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

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> getAll(@PageableDefault(size = 8) Pageable pageable) {
        return ResponseEntity.ok(noteService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<NoteResponseDto>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(size = 8) Pageable pageable) {
        return ResponseEntity.ok(noteService.searchByTitle(title, pageable));
    }

    @GetMapping("/search/content")
    public ResponseEntity<Page<NoteResponseDto>> searchByContent(
            @RequestParam String content,
            @PageableDefault(size = 8) Pageable pageable) {
        return ResponseEntity.ok(noteService.searchByContent(content, pageable));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(@PathVariable Long id, @Valid @RequestBody NoteRequestDto request) {
        return ResponseEntity.ok(noteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}