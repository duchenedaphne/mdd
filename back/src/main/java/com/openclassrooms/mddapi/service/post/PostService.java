package com.openclassrooms.mddapi.service.post;

import java.util.List;

import com.openclassrooms.mddapi.model.Post;

public interface PostService {

	List<Post> getPosts();

    public Post create(Post post) throws Exception;

    public Post findById(Long id) throws Exception;

    public Post update(Long id, Post post) throws Exception;

    public String delete(Long id) throws Exception;

    public List<Post> findAll() throws Exception;
}
