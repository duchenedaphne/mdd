package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.service.comment.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentServiceImpl commentService;
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findAllByPostId(@PathVariable("id") String id) {
        return commentService.find_all_by_post_id(id);
    }
    
    /** 
     * @param commentDto
     * @param userDetails
     * @return ResponseEntity<?>
     */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create_comment(commentDto, userDetails);
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return commentService.find_by_id(id);
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return commentService.delete_comment(id);
    }
}
