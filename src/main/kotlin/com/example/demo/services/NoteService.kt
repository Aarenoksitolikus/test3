package com.example.demo.services

import com.example.demo.dtos.NoteDto
import com.example.demo.dtos.NoteResponseDto
import com.example.demo.entities.NoteEntity
import com.example.demo.exceptions.NoteNotFoundException
import com.example.demo.repositories.NoteRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NoteService(private val repository: NoteRepository) {

    fun getAll(pageable: Pageable): Page<NoteResponseDto> {
        return repository.findAllByDeletedAtIsNull(pageable).map { it.toResponseDto() }
    }

    fun getById(id: Long): NoteResponseDto {
        val note = repository.findByIdAndDeletedAtIsNull(id) ?: throw NoteNotFoundException(id)
        return note.toResponseDto()
    }

    fun searchByTitle(title: String): List<NoteResponseDto> {
        return repository.findAllByTitleContainingIgnoreCaseAndDeletedAtIsNull(title).map { it.toResponseDto() }
    }

    fun searchByContent(content: String): List<NoteResponseDto> {
        return repository.findAllByContentContainingIgnoreCaseAndDeletedAtIsNull(content).map { it.toResponseDto() }
    }

    fun create(dto: NoteDto): NoteResponseDto {
        val note = NoteEntity(title = dto.title, content = dto.content)
        return repository.save(note).toResponseDto()
    }

    fun update(id: Long, dto: NoteDto): NoteResponseDto {
        val note = repository.findByIdAndDeletedAtIsNull(id) ?: throw NoteNotFoundException(id)
        note.title = dto.title
        note.content = dto.content
        note.updatedAt = System.currentTimeMillis()
        return repository.save(note).toResponseDto()
    }

    fun delete(id: Long) {
        val note = repository.findByIdAndDeletedAtIsNull(id) ?: throw NoteNotFoundException(id)
        note.deletedAt = System.currentTimeMillis()
        repository.save(note)
    }

    private fun NoteEntity.toResponseDto() = NoteResponseDto(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
