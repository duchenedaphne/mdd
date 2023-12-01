package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    componentModel = "spring", 
    imports = {Post.class}
)
public abstract class PostMapper implements EntityMapper<PostDto, Post> {
        
    @Autowired
    UserRepository userRepository;
    @Autowired
    TopicRepository topicRepository;

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(target = "author", expression = "java(postDto.getUser_id() != null ? this.userRepository.findById(Long.valueOf(postDto.getUser_id())).orElse(null) : null)"),
            @Mapping(target = "topic", expression = "java(postDto.getTopic_id() != null ? this.topicRepository.findById(Long.valueOf(postDto.getTopic_id())).orElse(null) : null)"),
    })
    public abstract Post toEntity(PostDto postDto);


    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "post.author.id", target = "user_id"),
            @Mapping(source = "post.topic.id", target = "topic_id"),
    })
    public abstract PostDto toDto(Post post);
}
