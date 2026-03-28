package org.example.test_maksik.service;

import lombok.RequiredArgsConstructor;
import org.example.test_maksik.dto.NoteRequestDto;
import org.example.test_maksik.dto.NoteResponseDto;
import org.example.test_maksik.entity.NoteEntity;
import org.example.test_maksik.exception.NotFoundException;
import org.example.test_maksik.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;
    private NoteResponseDto toDto(NoteEntity note){
        return NoteResponseDto.builder().id(note.getId()).title(note.getTitle()).content(note.getContent())
                .createdAt(note.getCreatedAt()).updatedAt(note.getUpdatedAt()).build();
    }

    public Page<NoteResponseDto> getAll(Pageable pageable){
        return repository.findAllByDeletedAtIsNull(pageable).map(this::toDto);
    }

    public NoteResponseDto getById(Long id){
        NoteEntity note = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("Note not foun d"));
        return toDto(note);
    }

    public NoteResponseDto create(NoteRequestDto dto){
        NoteEntity note = NoteEntity.builder().title(dto.getTitle()).content(dto.getContent())
                .createdAt(LocalDateTime.now()).build();
        return toDto(repository.save(note));
    }

    public NoteResponseDto update(Long id, NoteRequestDto dto){
        NoteEntity note = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("Note not foun d"));
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setUpdatedAt(LocalDateTime.now());
        return toDto(repository.save(note));
    }

    public void delete(Long id){
        NoteEntity note = repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("Note not foun d"));
        note.setDeletedAt(LocalDateTime.now());
        repository.save(note);
    }

    public List<NoteResponseDto> searchByTitle(String title){
        return repository.findByTitleContainingIgnoreCaseAndDeletedAtIsNull(title).stream().map(this::toDto).toList();
    }

    public List<NoteResponseDto> searchByContent(String content){
        return repository.findByContentContainingIgnoreCaseAndDeletedAtIsNull(content).stream().map(this::toDto).toList();
    }
}
