package com.openclassrooms.mddapi.service.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    @Override
    public User create(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws Exception {
        
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }

    @Override
    public User update(Long id, User user) throws Exception {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public String delete(Long id) throws Exception {
        
        userRepository.deleteById(id);
        return "Le compte a bien été supprimé !";
    }

    @Override
    public User findByEmail(String email) throws Exception {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }
    
    public ResponseEntity<?> fetchUser(Long id) {

        User user = new User();
        HttpStatus status;
        
        try {
            user = findById(id);
            
        } catch (HttpStatusCodeException exception) {

            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de la location.", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @Override
    public User findByUserName(String username) throws Exception {
        
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }

    @Override
    public Boolean existsByEmail(String email) throws Exception {
        return userRepository.existsByEmail(email);
    }

    public ResponseEntity<?> find_by_id(String id) {
        try {
            User user = findById(Long.valueOf(id));

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.userMapper.toDto(user));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> delete_account(String id) {
        
        try {
            User user = findById(Long.valueOf(id));

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!Objects.equals(userDetails.getUsername(), user.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            delete(Long.parseLong(id));
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}