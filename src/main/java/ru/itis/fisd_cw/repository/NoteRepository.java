package ru.itis.fisd_cw.repository;


import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.fisd_cw.data.entity.NoteEntity;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title);
    List<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(String content);

    List<NoteEntity> findByIdAndDeletedAtIsNull(Long id);

}
