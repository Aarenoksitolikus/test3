package ru.itis.fisd_cw.data.entity;


import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "notes")
@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 32)
    String title;


    @Column(name = "content", nullable = false, length = 128)
    String content;



    //Нужно было snakecase, но я забыл и в бд сделал кемел
    @Column(name = "createdat", nullable = false)
    LocalDateTime creationAt;


    //Нужно было snakecase, но я забыл и в бд сделал кемел
    @Column(name = "updatedat")
    LocalDateTime updatedAt;


    //Нужно было snakecase, но я забыл и в бд сделал кемел
    @Column(name = "deletedat")
    LocalDateTime deletedAt;

    @PreUpdate
    protected void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

    public void delete() {
        setDeletedAt(LocalDateTime.now());
    }

    @PrePersist
    protected void onCreate() {
        setCreationAt(LocalDateTime.now());
    }

}
