package control.work.infrastructure.persistence.repository;

import control.work.infrastructure.persistence.entity.NoteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaNoteRepository implements NoteRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(NoteEntity note) {
        entityManager.persist(note);
    }

    @Override
    public NoteEntity getById(UUID id) {
        return entityManager.find(NoteEntity.class, id);
    }

    @Override
    public List<NoteEntity> getByTitle(String title) {
        String jpql = "SELECT n FROM note n WHERE n.title LIKE %:title%";
        return entityManager.createQuery(jpql, NoteEntity.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public List<NoteEntity> getByContent(String content) {
        String jpql = "SELECT n FROM note n WHERE n.content LIKE %:content%";
        return entityManager.createQuery(jpql, NoteEntity.class)
                .setParameter("content", content)
                .getResultList();
    }

    @Override
    public List<NoteEntity> getAll(int pageNum, int size) {
        String jpql = "SELECT n FROM note n";
        return entityManager.createQuery(jpql, NoteEntity.class)
                .setFirstResult(pageNum * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public void update(NoteEntity note) {
        entityManager.merge(note);
    }

    @Override
    public void delete(UUID id) {

    }
}
