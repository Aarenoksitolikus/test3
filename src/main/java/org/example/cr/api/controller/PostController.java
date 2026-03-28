package org.example.cr.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cr.api.dto.PostDto;
import org.example.cr.api.mapper.PostMapper;
import org.example.cr.application.entities.PostEntity;
import org.example.cr.application.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    PostService postService;

    @GetMapping
    @RequestMapping("/get/{post-id}")
    public ResponseEntity<PostDto> getPost(@PathVariable(name = "post-id") Long postId){
        return ResponseEntity.ok().body(PostMapper.postDto(postService.getPostById(postId)));
    }

    @PostMapping
    @RequestMapping("/save")
    public ResponseEntity<Void> savePost(@RequestBody PostDto postDto){
        log.info("Saving post: {}", postDto.getTitle());
        postService.savePost(PostMapper.toModel(postDto));
        return ResponseEntity.ok().build();
    }


    @PutMapping
    @RequestMapping("/update/{post-id}")
    public ResponseEntity<Void> updatePost(@PathVariable(name = "post-id") Long postId,
                                           String title, String content){
        postService.updateById(postId, title, content);
        return ResponseEntity.ok().build();
    }

     @DeleteMapping
     @RequestMapping("/delete/{post-id}")
     public ResponseEntity<Void> deletePost(@PathVariable(name = "post-id") Long postId){
         postService.deleteById(postId);
         return ResponseEntity.ok().build();
     }

     @GetMapping
     @RequestMapping("/getAll")
        public ResponseEntity<Iterable<PostDto>> getAllPosts(){
            List<PostEntity> postEntities = postService.getAllPosts();
            return ResponseEntity.ok().body(postEntities.stream().map(PostMapper::postDto).toList());
    }

    @GetMapping
    @RequestMapping("/getByTitle/{title}")
    public ResponseEntity<Iterable<PostDto>> getPostsByTitle(@PathVariable(name = "title") String title){
        List<PostEntity> postEntities = postService.findByTitleContaining(title);
        return ResponseEntity.ok().body(postEntities.stream().map(PostMapper::postDto).toList());
    }

    @GetMapping
    @RequestMapping("/getByContent/{content}")
    public ResponseEntity<Iterable<PostDto>> getPostsByContent(@PathVariable(name = "content") String content){
        List<PostEntity> postEntities = postService.findByContentContaining(content);
        return ResponseEntity.ok().body(postEntities.stream().map(PostMapper::postDto).toList());
    }

}
