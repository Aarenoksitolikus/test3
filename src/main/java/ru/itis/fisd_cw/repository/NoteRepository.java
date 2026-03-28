package ru.itis.fisd_cw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.fisd_cw.data.entity.NoteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findByTitleContainingIgnoreCaseAndDeletedAtIsNull(String title);
    List<NoteEntity> findByContentContainingIgnoreCaseAndDeletedAtIsNull(String content);

    NoteEntity findByIdAndDeletedAtIsNull(Long id);

    Page<NoteEntity> findAllByDeletedAtIsNull(Pageable pageable);

}
