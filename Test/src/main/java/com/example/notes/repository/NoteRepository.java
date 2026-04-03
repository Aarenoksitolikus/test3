package com.example.notes.repository;

import com.example.notes.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    Page<NoteEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<NoteEntity> findByContentContainingIgnoreCase(String content, Pageable pageable);
}