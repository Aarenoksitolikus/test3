package control.work.service;

import control.work.infrastructure.persistence.entity.NoteEntity;
import control.work.infrastructure.persistence.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public void create(String title, String content){
        if (title != null && !title.isBlank()){
            noteRepository.create(
                    NoteEntity.builder()
                            .title(title)
                            .content(content)
                            .build()
            );
        }
    }
    public NoteEntity getById(UUID id){
        return noteRepository.getById(id);
    }

}
