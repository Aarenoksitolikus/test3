package com.example.KRtest3.repository;

import com.example.KRtest3.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE " +
            "LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Note> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT n FROM Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :titlePart, '%'))")
    List<Note> findByTitleContainingIgnoreCase(@Param("titlePart") String titlePart);

    Optional<Note> findByIdAndDeletedAtIsNull(Long id);
    List<Note> findAllByDeletedAtIsNull();
}