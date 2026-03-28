package ru.kpfu.springkr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.springkr.dto.NoteCreateDto;
import ru.kpfu.springkr.dto.NoteResponseDto;
import ru.kpfu.springkr.exception.EmptyParameterException;
import ru.kpfu.springkr.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Page<NoteResponseDto>> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(noteService.findAll(pageable));
    }

    @GetMapping("/title-match")
    public ResponseEntity<List<NoteResponseDto>> findAllByWordInTitle(@RequestParam String word) {
        return ResponseEntity.ok(noteService.findAllByWordInTitle(word));
    }

    @GetMapping("/content-match")
    public ResponseEntity<List<NoteResponseDto>> findAllByWordInContent(@RequestParam String word) {
        return ResponseEntity.ok(noteService.findAllByWordInContent(word));
    }

    @GetMapping("/match")
    public ResponseEntity<List<NoteResponseDto>> findAllByWord(
            @RequestParam(required = false) String titleContains,
            @RequestParam(required = false) String contentContains) {
        if (titleContains != null && contentContains != null) {
            return ResponseEntity.ok(noteService.findAllByWordsInTitleAndContent(titleContains, contentContains));
        } else if (titleContains != null) {
            return ResponseEntity.ok(noteService.findAllByWordInTitle(titleContains));
        } else if (contentContains != null) {
            return ResponseEntity.ok(noteService.findAllByWordInContent(contentContains));
        }

        throw new EmptyParameterException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> save(@RequestBody @Valid NoteCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noteService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(@PathVariable Long id, @RequestBody @Valid NoteCreateDto dto) {
        return ResponseEntity.ok(noteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        noteService.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
