package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", imports = {Arrays.class, Collectors.class, Topic.class, User.class, Collections.class, Optional.class})
public abstract class TopicMapper implements EntityMapper<TopicDto, Topic> {
        
    @Autowired
    UserRepository userRepository;

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(target = "users", expression = "java(Optional.ofNullable(topicDto.getUsers()).orElseGet(Collections::emptyList).stream().map(user_id -> { User user = this.userRepository.findById(user_id).orElse(null); if (user != null) { return user; } return null; }).collect(Collectors.toList()))"),
    })
    public abstract Topic toEntity(TopicDto topicDto);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(target = "users", expression = "java(Optional.ofNullable(topic.getUsers()).orElseGet(Collections::emptyList).stream().map(u -> u.getId()).collect(Collectors.toList()))"),
    })
    public abstract TopicDto toDto(Topic topic);
}
