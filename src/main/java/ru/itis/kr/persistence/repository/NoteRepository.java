package ru.itis.kr.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.kr.persistence.entity.NoteEntity;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    @Query("SELECT n FROM NoteEntity n WHERE " +
            "LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) AND " +
            "n.deletedAt IS NULL OR n.deletedAt > CURRENT_TIMESTAMP")
    List<NoteEntity> searchByTitle(@Param("query") String query);

    @Query("SELECT n FROM NoteEntity n WHERE " +
            "LOWER(n.content) LIKE LOWER(CONCAT('%', :query, '%')) AND " +
            "n.deletedAt IS NOT NULL OR n.deletedAt > CURRENT_TIMESTAMP")
    List<NoteEntity> searchByContent(@Param("query") String query);

    boolean existsByTitle(String title);
}
