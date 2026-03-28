package com.example.demo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "notes")
class NoteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 255)
    var title: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String = "",

    @Column(nullable = false, name = "created_at")
    var createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis(),

    @Column(name = "deleted_at")
    var deletedAt: Long? = null,
)
