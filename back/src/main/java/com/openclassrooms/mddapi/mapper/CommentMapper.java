package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    componentModel = "spring", 
    imports = {Comment.class}
)
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {
        
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Mappings({
            @Mapping(source = "content", target = "content"),
            @Mapping(target = "author", expression = "java(commentDto.getUser_id() != null ? this.userRepository.findById(Long.valueOf(commentDto.getUser_id())).orElse(null) : null)"),
            @Mapping(target = "post", expression = "java(commentDto.getPost_id() != null ? this.postRepository.findById(Long.valueOf(commentDto.getUser_id())).orElse(null) : null)"),
    })
    public abstract Comment toEntity(CommentDto commentDto);


    @Mappings({
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "comment.post.id", target = "post_id"),
            @Mapping(source = "comment.author.id", target = "user_id"),
    })
    public abstract CommentDto toDto(Comment comment);
}