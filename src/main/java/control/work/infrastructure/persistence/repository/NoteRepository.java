package control.work.infrastructure.persistence.repository;

import control.work.infrastructure.persistence.entity.NoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository {
    void create(NoteEntity note);
    @Query("SELECT n FROM note n WHERE n.id = :id")
    NoteEntity getById(@Param("id") UUID id);
    @Query("SELECT n FROM note n WHERE n.title LIKE %:title%")
    List<NoteEntity> getByTitle(@Param("title") String title);
    List<NoteEntity> getByContent(String content);
    List<NoteEntity> getAll(int pageNum, int size);
    void update(NoteEntity note);
    void delete(UUID id);
}
