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
    
    /** 
     * @return Post
     */
    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(target = "author", expression = "java(postDto.getUser_name() != null ? this.userRepository.findByUserName(postDto.getUser_name()).orElse(null) : null)"),
            @Mapping(target = "topic", expression = "java(postDto.getTopic_name() != null ? this.topicRepository.findByName(postDto.getTopic_name()).orElse(null) : null)"),
    })
    public abstract Post toEntity(PostDto postDto);

    
    /** 
     * @return PostDto
     */
    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "post.author.userName", target = "user_name"),
            @Mapping(source = "post.topic.name", target = "topic_name"),
    })
    public abstract PostDto toDto(Post post);
}
