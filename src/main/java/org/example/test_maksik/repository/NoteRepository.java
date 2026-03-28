package org.example.test_maksik.repository;

import org.example.test_maksik.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    Page<NoteEntity> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<NoteEntity> findByIdAndDeletedAtIsNull(Long id);
    List<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title);
    List<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(String content);

    String content(String content);
}
