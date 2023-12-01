package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.user.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return userService.find_by_id(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return userService.delete_account(id);
    }
}
