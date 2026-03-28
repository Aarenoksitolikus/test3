package ru.kpfu.springkr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.springkr.model.NoteEntity;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE NoteEntity n SET n.deletedAt = CURRENT TIMESTAMP WHERE n.id = :id")
    void softDelete(Long id);

    List<NoteEntity> findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String word1, String word2);

    List<NoteEntity> findAllByTitleContainingIgnoreCase(String word);

    List<NoteEntity> findAllByContentContainingIgnoreCase(String word);

    boolean existsByTitle(String title);

}
