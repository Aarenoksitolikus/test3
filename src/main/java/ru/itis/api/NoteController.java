package ru.itis.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dto.NoteCreateDto;
import ru.itis.api.dto.NoteResponseDto;
import ru.itis.service.NoteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService service;

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteCreateDto dto) {
        NoteResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NoteResponseDto> pageResult = service.getAll(pageable);

        return ResponseEntity.ok(pageResult.getContent());
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<NoteResponseDto>> searchByTitle(@RequestParam String title) {
        List<NoteResponseDto> notes = service.searchByTitle(title);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search/content")
    public ResponseEntity<List<NoteResponseDto>> searchByContent(@RequestParam String content) {
        List<NoteResponseDto> notes = service.searchByContent(content);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(@PathVariable UUID id, @Valid @RequestBody NoteCreateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}