package ru.kpfu.springkr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kpfu.springkr.dto.NoteCreateDto;
import ru.kpfu.springkr.dto.NoteResponseDto;
import ru.kpfu.springkr.exception.NoteNotFoundException;
import ru.kpfu.springkr.exception.TitleAlreadyExistsException;
import ru.kpfu.springkr.mapper.NoteMapper;
import ru.kpfu.springkr.model.NoteEntity;
import ru.kpfu.springkr.repository.NoteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper mapper;

    public Page<NoteResponseDto> findAll(Pageable pageable) {
        log.debug("IN NoteService find all, page: {}", pageable.getPageNumber());
        return noteRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    public NoteResponseDto findById(Long id) {
        log.debug("IN NoteService find by id {}", id);
        return noteRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public List<NoteResponseDto> findAllByWordInTitle(String word) {
        log.debug("IN NoteService find notes by {} in title", word);
        return noteRepository.findAllByTitleContainingIgnoreCase(word)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<NoteResponseDto> findAllByWordInContent(String word) {
        log.debug("IN NoteService find notes by {} in content", word);
        return noteRepository.findAllByContentContainingIgnoreCase(word)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<NoteResponseDto> findAllByWordsInTitleAndContent(String wordInTitle, String wordInContent) {
        log.debug("IN NoteService find all by [{}] in title and [{}] in content", wordInTitle, wordInContent);
        return noteRepository.findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(wordInTitle, wordInContent)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public NoteResponseDto save(NoteCreateDto dto) {
        log.debug("IN NoteService save {}", dto);
        NoteEntity note = mapper.fromDto(dto);

        if (noteRepository.existsByTitle(note.getTitle()))
            throw new TitleAlreadyExistsException(note.getTitle());

        note = noteRepository.save(note);
        log.info("Saved new note with id {}", note.getId());
        return mapper.toDto(note);
    }

    public NoteResponseDto update(Long id, NoteCreateDto dto) {
        log.debug("IN NoteService update note {}", id);
        NoteEntity note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if (noteRepository.existsByTitle(note.getTitle()))
            throw new TitleAlreadyExistsException(note.getTitle());

        note.setTitle(dto.title());
        note.setContent(dto.content());

        return mapper.toDto(noteRepository.save(note));
    }

    public void softDelete(Long id) {
        log.debug("IN NoteService soft delete note {}", id);
        noteRepository.softDelete(id);
    }

}
