package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.comment.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return commentService.find_by_id(id);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return commentService.find_all();
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CommentDto commentDto) {
        return commentService.create_comment(commentDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody CommentDto commentDto) {
        return commentService.update_comment(id, commentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return commentService.delete_comment(id);
    }
}
