package com.openclassrooms.mddapi.service.post;

import java.util.List;

import com.openclassrooms.mddapi.model.Post;

public interface PostService {

	public List<Post> getPosts();

    public List<Post> findAll() throws Exception;

    public List<Post> findAllByTopicId(Long id) throws Exception;

    public Post create(Post post) throws Exception;

    public Post findById(Long id) throws Exception;

    public String delete(Long id) throws Exception;
}
