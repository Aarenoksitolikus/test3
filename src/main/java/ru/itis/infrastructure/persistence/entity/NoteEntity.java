package ru.itis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "note", schema = "public")
@SQLDelete(sql = "update public.note set deleted_at = now() where id = ?")
@SQLRestriction("deleted_at is null")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        if (Objects.isNull(id)) {
            setId(UUID.randomUUID());

        }
        LocalDateTime now = LocalDateTime.now();
        setCreatedAt(now);
        setUpdatedAt(now);
    }

    @PreUpdate
    protected void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }
}
