package ru.kpfu.itis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(@Param("title") String title, Pageable pageable);

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.content) LIKE LOWER(CONCAT('%', :content, '%'))")
    Page<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(@Param("content") String content, Pageable pageable);

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL")
    Page<NoteEntity> findAllActive(Pageable pageable);
}
