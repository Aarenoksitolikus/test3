package ru.itis.fisd_cw.data.entity;


import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "notes")
@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 32)
    String title;


    @Column(name = "content", nullable = false, length = 128)
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
