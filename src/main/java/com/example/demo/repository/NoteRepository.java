package com.example.demo.repository;

import com.example.demo.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    //из таблицы нотэентити где в нижнем регистре сравнивается с таким же в нижнем регистре склеенным параметром
    //процент с 2 сторон значит содержит в любом месте
    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<NoteEntity> searchByTitle(@Param("title") String title, Pageable pageable);

    //уже в содержании по тому же принципу
    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL AND LOWER(n.content) LIKE LOWER(CONCAT('%', :content, '%'))")
    Page<NoteEntity> searchByContent(@Param("content") String content, Pageable pageable);

    //в целом все заметки
    @Query("SELECT n FROM NoteEntity n WHERE n.deletedAt IS NULL")
    Page<NoteEntity> findAllActive(Pageable pageable);
}
