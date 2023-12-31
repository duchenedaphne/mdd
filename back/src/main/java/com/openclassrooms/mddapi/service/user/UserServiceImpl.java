package com.openclassrooms.mddapi.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    
    /** 
     * @param user
     * @return User
     * @throws Exception
     */
    @Override
    public User create(User user) throws Exception {
        return userRepository.save(user);
    }
    
    /** 
     * @param id
     * @return User
     * @throws Exception
     */
    @Override
    public User findById(Long id) throws Exception {
        
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }
    
    /** 
     * @param id
     * @param user
     * @return User
     * @throws Exception
     */
    @Override
    public User update(Long id, User user) throws Exception {
        user.setId(id);
        return userRepository.save(user);
    }
    
    /** 
     * @param id
     * @return String
     * @throws Exception
     */
    @Override
    public String delete(Long id) throws Exception {
        
        userRepository.deleteById(id);
        return "Le compte a bien été supprimé !";
    }
    
    /** 
     * @param email
     * @return User
     * @throws Exception
     */
    @Override
    public User findByEmail(String email) throws Exception {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }
    
    /** 
     * @return List<User>
     * @throws Exception
     */
    @Override
    public List<User> findAll() throws Exception {
        return userRepository.findAll();
    }    
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
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
    
    /** 
     * @param username
     * @return User
     * @throws Exception
     */
    @Override
    public User findByUserName(String username) throws Exception {
        
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Aucun compte trouvé.");
        }
    }
    
    /** 
     * @param email
     * @return Boolean
     * @throws Exception
     */
    @Override
    public Boolean existsByEmail(String email) throws Exception {
        return userRepository.existsByEmail(email);
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
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
    
    /** 
     * @param id
     * @param userDto
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> update_account(String id, UserDto userDto) {

        Matcher matcher = emailFormatChecker(userDto.getEmail());
        if (matcher.matches() == false)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Format email invalide."));

        HttpStatus status;
        User user;
        User userUpdated = new User();
        try {
            user = findById(Long.valueOf(id));

            if (user == null)
                return ResponseEntity.notFound().build();

            userUpdated.setPassword(user.getPassword());
            userUpdated.setCreatedAt(user.getCreatedAt());
            userUpdated.setUpdatedAt(LocalDateTime.now());
            userUpdated.setUserName(userDto.getUserName());
            userUpdated.setEmail(userDto.getEmail());
                
            if (!userDto.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userDto.getEmail()))
                return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Un autre compte existe déjà avec cette adresse email !"));

            update(Long.valueOf(id), userUpdated);
                        
        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Mise à jour impossible.", status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new MessageResponse("Compte mis à jour avec succès!")); 
    }
    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> delete_account(String id) {
        
        try {
            User user = findById(Long.valueOf(id));

            if (user == null) 
                return ResponseEntity.notFound().build();
            
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!Objects.equals(userDetails.getUsername(), user.getEmail())) 
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            
            delete(Long.parseLong(id));
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    /** 
     * @param email
     * @return Matcher
     */
    public Matcher emailFormatChecker(String email) {
        
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return matcher;
    }   
}
