package ru.itis.kr.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "note")
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Требуется заголовок")
    @Size(max = 255, message = "Превышен лимит символов (255)")
    private String title;

    @Builder.Default
    @Column(length = 65536, nullable = false)
    @Size(max = 65536, message = "Превышен лимит символов (65536)")
    private String content = "";

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime deletedAt;

    public boolean isDeleted() {
        return deletedAt != null && deletedAt.isBefore(LocalDateTime.now());
    }
}
