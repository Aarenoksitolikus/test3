package ru.itis.fisd_cw.entity;


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

    @Column(name = "title")
    String title;


    @Column(name = "content")
    String content;


    @Column(name = "createdAt", nullable = false)
    LocalDateTime creationAt;

    @Column(name = "updatedAt")
    LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    LocalDateTime deletedAt;

}
