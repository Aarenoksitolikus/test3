package ru.itis.persistence.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.itis.persistence.entity.NoteEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NoteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public NoteEntity save(NoteEntity note) {
        if (note.getId() == null) {
            entityManager.persist(note);
            return note;
        } else {
            return entityManager.merge(note);
        }
    }

    public Optional<NoteEntity> findById(UUID id) {
        NoteEntity note = entityManager.find(NoteEntity.class, id);
        return Optional.ofNullable(note);
    }

    public List<NoteEntity> findAll() {
        String sql = "select note from NoteEntity note";
        TypedQuery<NoteEntity> query = entityManager.createQuery(sql, NoteEntity.class);
        return query.getResultList();
    }
}
