package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  Optional<User> findByEmail(String email);

  Optional<User> findByUserName(String username);

  Boolean existsByEmail(String email); 
}
