package com.oris.notes.repository;

import com.oris.notes.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    Optional<NoteEntity> findByIdAndDeletedAtIsNull(Long id);

    Page<NoteEntity> findAllByDeletedAtIsNull(Pageable pageable);

    Page<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title, Pageable pageable);

    Page<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(String content, Pageable pageable);
}

