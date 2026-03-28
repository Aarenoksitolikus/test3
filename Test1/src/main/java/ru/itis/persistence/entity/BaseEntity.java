package ru.itis.persistence.entity;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(name= "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "creation_at", nullable = false)
    LocalDateTime creationAt;

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = false)
    LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        if (Objects.isNull(id)) {
            setId(UUID.randomUUID());
        }
        LocalDateTime now = LocalDateTime.now();
        setCreationAt(now);
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
