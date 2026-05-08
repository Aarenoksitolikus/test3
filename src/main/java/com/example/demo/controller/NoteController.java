package com.example.demo.controller;

import com.example.demo.dto.NoteRequestDto;
import com.example.demo.dto.NoteResponseDto;
import com.example.demo.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService noteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    public NoteResponseDto create(@Valid @RequestBody NoteRequestDto request) {
        return noteService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)  // 200
    public NoteResponseDto getById(@PathVariable Long id) {
        return noteService.getById(id);
    }
    

    @GetMapping
    @ResponseStatus(HttpStatus.OK)  // 200
    public Page<NoteResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,//c 0 cтраницы и сколько типо на ней должно быть
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noteService.getAll(pageable);
    }
    

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)  // 200
    public NoteResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequestDto request) {
        return noteService.update(id, request);
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
    

    @GetMapping("/search/title")
    @ResponseStatus(HttpStatus.OK)  // 200
    public Page<NoteResponseDto> searchByTitle(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noteService.searchByTitle(query, pageable);
    }
    

    @GetMapping("/search/content")
    @ResponseStatus(HttpStatus.OK)  // 200
    public Page<NoteResponseDto> searchByContent(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noteService.searchByContent(query, pageable);
    }
}