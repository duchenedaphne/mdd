package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    Optional<Post> findByTitle(String title);

    List<Post> findAllByTopic(Topic topic);
}
