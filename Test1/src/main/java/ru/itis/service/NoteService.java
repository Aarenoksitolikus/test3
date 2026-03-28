package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.persistence.entity.NoteEntity;
import ru.itis.persistence.repository.NoteRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;

    @Transactional
    public NoteEntity saveNote(String title, String content, LocalDate creationAt) {
        NoteEntity note = NoteEntity.builder()
                .title(title)
                .content(content)
                .creationAt(creationAt)
                .build();

        return repository.save(note);
    }

    @Transactional
    public Optional<NoteEntity> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void updateNote(UUID id, String title, String content, LocalDate updatedAt) {
        Optional<NoteEntity> note = repository.findById(id);

        if (!note.isEmpty()) {
            NoteEntity noteEntity = note.get();
            noteEntity.setTitle(title);
            noteEntity.setContent(content);
            noteEntity.setUpdatedAt(updatedAt);
            repository.save(noteEntity);
        } else {
            System.out.println("Не удалось изменить заметку");
        }
    }

    @Transactional
    public void deleteNote(UUID id, LocalDate deletedAt) {
        Optional<NoteEntity> note = repository.findById(id);

        if (!note.isEmpty()) {
            NoteEntity noteEntity = note.get();
            noteEntity.setDeletedAt(deletedAt);
            repository.save(noteEntity);
        } else {
            System.out.println("Не удалось удалить заметку");
        }
    }

    @Transactional
    public Page<NoteEntity> findNotesByTitle(String title) {
        return null;
    }

    @Transactional
    public Page<NoteEntity> findNotesByContent(String content) {
        return null;
    }

    @Transactional
    public Page<NoteEntity> findAll(Pageable pageable) {
        return null;
    }
}
