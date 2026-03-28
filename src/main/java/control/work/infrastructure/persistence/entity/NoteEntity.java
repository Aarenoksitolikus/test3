package control.work.infrastructure.persistence.entity;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "note", schema = "public")
public class NoteEntity {
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    @Size(min=1, max=64)
    private String title;

    @Column
    @Size(max=512)
    private String content;

    @NotBlank
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotBlank
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

    @PreDestroy
    protected void preDestroy() {
        setDeletedAt(LocalDateTime.now());
    }
}
