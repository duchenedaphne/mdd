package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.service.topic.TopicServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {
	
	private final TopicServiceImpl topicService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return topicService.find_by_id(id);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return topicService.find_all();
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody TopicDto topicDto) {
        return topicService.create_topic(topicDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody TopicDto topicDto) {
        return topicService.update_topic(id, topicDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return topicService.delete_topic(id);
    }

    @PostMapping("{id}/subscribe/{userId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        return topicService.subscription(id, userId);
    }

    @DeleteMapping("{id}/subscribe/{userId}")
    public ResponseEntity<?> unSubscribe(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        return topicService.subscription_cancelling(id, userId);
    }
}
