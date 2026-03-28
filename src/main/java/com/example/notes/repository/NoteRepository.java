package com.example.notes.repository;

import com.example.notes.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    Page<NoteEntity> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<NoteEntity> findByIdAndDeletedAtIsNull(Long id);

    List<NoteEntity> findAllByDeletedAtIsNotNull();

    List<NoteEntity> findAllByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title);
}
