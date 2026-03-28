package com.example.kr.kr.persistence.repository;

import com.example.kr.kr.persistence.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, Long>{

}
