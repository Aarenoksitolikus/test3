package ru.itis.fisd_cw.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Repository;
import ru.itis.fisd_cw.entity.NoteEntity;

@Repository
public class NoteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveNote(NoteEntity noteEntity) {
        if (noteEntity.getId() == null) {
            entityManager.persist(noteEntity);
        } else {
            entityManager.merge(noteEntity);
        }
    }

    //FIRST GET
    public NoteEntity findByID(Long id) {
        return entityManager.find(NoteEntity.class, id);
    }

    public NoteEntity findAll() {
        return entityManager.find()
    }


    //DELETE
    @Transactional
    public void deleteById(Long id) {
        NoteEntity noteEntity = findByID(id);

        if (noteEntity != null) {
            entityManager.remove(noteEntity);
        }
    }
}
