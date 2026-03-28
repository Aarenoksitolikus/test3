package org.example.test_maksik.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.test_maksik.dto.NoteRequestDto;
import org.example.test_maksik.dto.NoteResponseDto;
import org.example.test_maksik.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;
    @GetMapping
    public Page<NoteResponseDto> getAll(Pageable pageable){
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public NoteResponseDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @GetMapping("/search/title")
    public List<NoteResponseDto> searchByTitle(@RequestParam String title){
        return service.searchByTitle(title);
    }

    @GetMapping("/search/content")
    public List<NoteResponseDto> searchByContent(@RequestParam String content){
        return service.searchByContent(content);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponseDto create(@Valid @RequestBody NoteRequestDto dto){
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public NoteResponseDto update(@PathVariable Long id, @Valid @RequestBody NoteRequestDto dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
