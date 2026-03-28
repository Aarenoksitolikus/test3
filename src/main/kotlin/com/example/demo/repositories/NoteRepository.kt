package com.example.demo.repositories

import com.example.demo.entities.NoteEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<NoteEntity, Long> {

    fun findAllByDeletedAtIsNull(pageable: Pageable): Page<NoteEntity>

    fun findByIdAndDeletedAtIsNull(id: Long): NoteEntity?

    fun findAllByTitleContainingIgnoreCaseAndDeletedAtIsNull(title: String): List<NoteEntity>

    fun findAllByContentContainingIgnoreCaseAndDeletedAtIsNull(content: String): List<NoteEntity>
}
