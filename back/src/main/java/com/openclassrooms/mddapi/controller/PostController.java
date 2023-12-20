package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.service.post.PostServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostServiceImpl postService;    
    
    /** 
     * @return ResponseEntity<?>
     */
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return postService.find_all();
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @GetMapping("/topic/{id}")
    public ResponseEntity<?> findAllByTopicId(@PathVariable("id") String id) {
        return postService.find_all_by_topic_id(id);
    }
    
    /** 
     * @param postDto
     * @param userDetails
     * @return ResponseEntity<?>
     */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.create_post(postDto, userDetails);
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return postService.find_by_id(id);
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return postService.delete_post(id);
    }
}
