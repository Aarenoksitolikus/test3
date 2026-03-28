package org.example.cr.application.service;

import lombok.AllArgsConstructor;
import org.example.cr.api.dto.ApiErrorResponseDto;
import org.example.cr.application.entities.PostEntity;
import org.example.cr.application.exception.NotExistException;
import org.example.cr.application.repository.IPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private IPostRepository iPostRepository;

    public void savePost(PostEntity postEntity){
        iPostRepository.save(postEntity);
    }

    public void updateById(Long id, String title, String content) {

        PostEntity postEntity = iPostRepository.findById(id)
                .orElseThrow(() -> new NotExistException(
                        new ApiErrorResponseDto("не существует такой записи, чтобы ее обновить", "400")
                ));

        postEntity.setTitle(title);
        postEntity.setContent(content);

        iPostRepository.updateUpdatedAt(id);
        iPostRepository.save(postEntity);
    }

    public void deleteById(Long id){
        if (!iPostRepository.existsById(id)) {
            throw new NotExistException(new ApiErrorResponseDto( "не существует такой записи, чтобы ее удалить", "400"));
        }
        iPostRepository.markAsDeleted(id);
    }

    public PostEntity getPostById(Long id){
        PostEntity postEntity = iPostRepository.findById(id)
                .orElseThrow(() -> new NotExistException(
                        new ApiErrorResponseDto("не существует такой записи, чтобы ее получить", "400")
        ));

        return postEntity;
    }

    public List<PostEntity> getAllPosts(){
        return iPostRepository.findAll();
    }

    public List<PostEntity> findByTitleContaining(String substring) {
        return iPostRepository.findByTitleContaining(substring);
    }

    public List<PostEntity> findByContentContaining(String substring) {
        return iPostRepository.findByContentContaining(substring);
    }


}
