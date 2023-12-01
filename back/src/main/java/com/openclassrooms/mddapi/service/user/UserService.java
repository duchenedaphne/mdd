package com.openclassrooms.mddapi.service.user;

import java.util.List;

import com.openclassrooms.mddapi.model.User;

public interface UserService {
    
    public User create(User user) throws Exception;

    public User findById(Long id) throws Exception;

    public User update(Long id, User user) throws Exception;

    public String delete(Long id) throws Exception;

    public List<User> findAll() throws Exception;

    public User findByEmail(String email) throws Exception;

    public User findByUserName(String username) throws Exception;

    public Boolean existsByEmail(String email) throws Exception;
}
