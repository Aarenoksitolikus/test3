package ru.itis.kr.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.kr.persistence.dto.NoteDto;
import ru.itis.kr.persistence.entity.NoteEntity;
import ru.itis.kr.persistence.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<NoteDto> searchByTitle(String query) {
        List<NoteEntity> notes = noteRepository.searchByTitle(query);
        List<NoteDto> result = new ArrayList<>();
        for (NoteEntity note : notes) {
            result.add(NoteDto.builder()
                    .id(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .createdAt(note.getCreatedAt())
                    .updatedAt(note.getUpdatedAt())
                    .build());
        }
        return result;
    }

    public List<NoteDto> searchByContent(String query) {
        List<NoteEntity> notes = noteRepository.searchByContent(query);
        List<NoteDto> result = new ArrayList<>();
        for (NoteEntity note : notes) {
            result.add(NoteDto.builder()
                    .id(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .createdAt(note.getCreatedAt())
                    .updatedAt(note.getUpdatedAt())
                    .build());
        }
        return result;
    }

    public Optional<NoteDto> findById(long id) {
        NoteEntity note = noteRepository.findById(id).orElse(null);
        if (note == null || note.isDeleted()) {
            return Optional.empty();
        }
        return Optional.of(NoteDto.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .createdAt(note.getCreatedAt())
                        .updatedAt(note.getUpdatedAt())
                .build());
    }

    public List<NoteDto> getAll(int pageNumber, int pageSize) {
        pageNumber = Math.clamp(pageNumber, 1, Math.max(pageNumber, 0));
        Pageable page = PageRequest.of(pageNumber - 1, pageSize);
        List<NoteDto> result = new ArrayList<>();
        noteRepository.findAll(page).forEach(note -> {
            if (!note.isDeleted()) {
                result.add(NoteDto.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .createdAt(note.getCreatedAt())
                        .updatedAt(note.getUpdatedAt())
                        .build());
            }
        });
        return result;
    }

    public Optional<NoteDto> create(String title, String content) {
        if (noteRepository.existsByTitle(title)) {
            return Optional.empty();
        }
        NoteEntity note = noteRepository.save(NoteEntity.builder()
                .title(title)
                .content(content == null ? "" : content)
                .build());
        return Optional.of(NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build());
    }

    public boolean delete(long id) {
        NoteEntity note = noteRepository.findById(id).orElse(null);
        if (note == null || note.isDeleted()) {
            return false;
        }
        note.setDeletedAt(LocalDateTime.now());
        noteRepository.save(note);
        return true;
    }
}
