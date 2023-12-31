package com.openclassrooms.mddapi.service.comment;

import java.util.List;

import com.openclassrooms.mddapi.model.Comment;

public interface CommentService {
    
	public List<Comment> getComments();

    public List<Comment> findAllByPostId(Long id) throws Exception;

    public Comment create(Comment comment) throws Exception;

    public Comment findById(Long id) throws Exception;

    public String delete(Long id) throws Exception;

    public List<Comment> findAll() throws Exception;
}
