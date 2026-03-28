package ru.itis.fisd_cw.entity;


import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    String title;


    @Column(name = "content", nullable = false)
    String content;


    @Column(name = "createdAt", nullable = false)
    LocalDateTime creationAt;

    @Column(name = "updatedAt")
    LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    LocalDateTime deletedAt;

    @PreUpdate
    protected void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

    protected void preDelete() {
        setDeletedAt(LocalDateTime.now());
    }

    @PrePersist
    protected void onCreate() {
        setCreationAt(LocalDateTime.now());
    }

}
