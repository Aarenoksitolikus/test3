package ru.itis.fisd_cw.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.fisd_cw.entity.NoteEntity;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title);

    List<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(String content);
}
