package org.example.cr.application.repository;

import org.example.cr.application.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByTitleContaining(String substring);

    List<PostEntity> findByContentContaining(String substring);

    @Modifying
    @Query("UPDATE PostEntity p SET p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    void updateUpdatedAt(@Param("id") Long id);

    @Modifying
    @Query("UPDATE PostEntity p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    void markAsDeleted(@Param("id") Long id);
}
