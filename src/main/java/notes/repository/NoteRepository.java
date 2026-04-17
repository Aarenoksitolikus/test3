package notes.repository;

import notes.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<NoteEntity> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.content) LIKE LOWER(CONCAT('%', :content, '%'))")
    Page<NoteEntity> findByContentContainingIgnoreCase(@Param("content") String content, Pageable pageable);

    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL")
    Page<NoteEntity> findAllActive(Pageable pageable);

    @Query("SELECT n FROM NoteEntity n WHERE n.id = :id AND n.deletedAt IS NULL")
    Optional<NoteEntity> findActiveById(@Param("id") Long id);
}