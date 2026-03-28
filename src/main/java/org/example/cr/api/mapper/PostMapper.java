package org.example.cr.api.mapper;

import org.example.cr.api.dto.PostDto;
import org.example.cr.application.entities.PostEntity;

public class PostMapper {
    public static PostEntity toModel(PostDto postDto){
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(postDto.getContent());
        postEntity.setTitle(postDto.getTitle());
        return postEntity;
    }

    public static PostDto postDto(PostEntity postEntity){
        return new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getDeletedAt()
        );
    }
}
