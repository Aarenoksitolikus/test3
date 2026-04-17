package notes.service;

import notes.dto.NoteRequestDto;
import notes.dto.NoteResponseDto;
import notes.entity.NoteEntity;
import notes.exception.NoteNotFoundException;
import notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteResponseDto create(NoteRequestDto request) {
        NoteEntity entity = new NoteEntity();
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        return toResponse(noteRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public NoteResponseDto findById(Long id) {
        NoteEntity entity = noteRepository.findActiveById(id)
                .orElseThrow(() -> new NoteNotFoundException("Заметка с id " + id + " не найдена"));
        return toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<NoteResponseDto> findAll(Pageable pageable) {
        return noteRepository.findAllActive(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<NoteResponseDto> searchByTitle(String title, Pageable pageable) {
        return noteRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<NoteResponseDto> searchByContent(String content, Pageable pageable) {
        return noteRepository.findByContentContainingIgnoreCase(content, pageable).map(this::toResponse);
    }

    @Transactional
    public NoteResponseDto update(Long id, NoteRequestDto request) {
        NoteEntity entity = noteRepository.findActiveById(id)
                .orElseThrow(() -> new NoteNotFoundException("Заметка с id " + id + " не найдена"));
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setUpdatedAt(LocalDateTime.now());
        return toResponse(noteRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        NoteEntity entity = noteRepository.findActiveById(id)
                .orElseThrow(() -> new NoteNotFoundException("Заметка с id " + id + " не найдена"));
        entity.setDeletedAt(LocalDateTime.now());
        noteRepository.save(entity);
    }

    private NoteResponseDto toResponse(NoteEntity entity) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}