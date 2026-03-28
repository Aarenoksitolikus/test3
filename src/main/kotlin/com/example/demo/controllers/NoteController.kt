package com.example.demo.controllers

import com.example.demo.dtos.NoteDto
import com.example.demo.dtos.NoteResponseDto
import com.example.demo.services.NoteService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notes")
class NoteController(private val service: NoteService) {

    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<NoteResponseDto>> {
        return ResponseEntity.ok(service.getAll(pageable))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<NoteResponseDto> {
        return ResponseEntity.ok(service.getById(id))
    }

    @GetMapping("/search/title")
    fun searchByTitle(@RequestParam title: String): ResponseEntity<List<NoteResponseDto>> {
        return ResponseEntity.ok(service.searchByTitle(title))
    }

    @GetMapping("/search/content")
    fun searchByContent(@RequestParam content: String): ResponseEntity<List<NoteResponseDto>> {
        return ResponseEntity.ok(service.searchByContent(content))
    }

    @PostMapping
    fun create(@RequestBody @Valid dto: NoteDto): ResponseEntity<NoteResponseDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid dto: NoteDto): ResponseEntity<NoteResponseDto> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
