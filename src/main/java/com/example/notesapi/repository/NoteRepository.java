package com.example.notesapi.repository;

import com.example.notesapi.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByDeletedAtIsNull(Pageable pageable);

    java.util.Optional<Note> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT n FROM Note n WHERE n.deletedAt IS NULL AND LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Note> findByTitleContaining(@Param("query") String query, Pageable pageable);

    @Query("SELECT n FROM Note n WHERE n.deletedAt IS NULL AND LOWER(n.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Note> findByContentContaining(@Param("query") String query, Pageable pageable);
}


