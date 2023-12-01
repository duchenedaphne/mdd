package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.service.post.PostServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return postService.find_by_id(id);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return postService.find_all();
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto) {
        return postService.create_post(postDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody PostDto postDto) {
        return postService.update_post(id, postDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return postService.delete_post(id);
    }
}
