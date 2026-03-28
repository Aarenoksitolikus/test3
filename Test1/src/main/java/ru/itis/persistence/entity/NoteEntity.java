package ru.itis.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class NoteEntity extends BaseEntity {
    private LocalDate creationAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "content", columnDefinition = "VARCHAR(1024)")
    private String content;
}