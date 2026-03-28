package ru.itis.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.infrastructure.persistence.entity.NoteEntity;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findByTitleContainingIgnoreCase(String titlePart);

    List<NoteEntity> findByContentContainingIgnoreCase(String contentPart);
}
