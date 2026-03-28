package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.api.dto.NoteCreateDto;
import ru.itis.api.dto.NoteResponseDto;
import ru.itis.api.exception.NoteNotFoundException;
import ru.itis.infrastructure.persistence.entity.NoteEntity;
import ru.itis.infrastructure.persistence.repository.NoteRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;

    @Transactional
    public NoteResponseDto create(NoteCreateDto dto) {
        NoteEntity entity = NoteEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        NoteEntity saved = repository.save(entity);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public NoteResponseDto getById(UUID id) {
        NoteEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<NoteResponseDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    @Transactional
    public NoteResponseDto update(UUID id, NoteCreateDto dto) {
        NoteEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        return toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NoteNotFoundException("Note not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<NoteResponseDto> searchByTitle(String titlePart) {
        return repository.findByTitleContainingIgnoreCase(titlePart)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteResponseDto> searchByContent(String contentPart) {
        return repository.findByContentContainingIgnoreCase(contentPart)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private NoteResponseDto toDto(NoteEntity entity) {
        return NoteResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
