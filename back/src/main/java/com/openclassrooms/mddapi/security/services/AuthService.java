package com.openclassrooms.mddapi.security.services;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.service.user.UserServiceImpl;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserServiceImpl userServiceImpl;

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        User user;
        HttpStatus status;
        Matcher matcher = emailFormatChecker(loginRequest.getEmail());

        try {
            if (matcher.matches() == false)
                user = userServiceImpl.findByUserName(loginRequest.getEmail());
            else               
                user = userServiceImpl.findByEmail(loginRequest.getEmail());                

            if (user == null)
                return new ResponseEntity<String>("Identifiant inconnu, veuillez créer un compte.", HttpStatus.NOT_FOUND);
                
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {                 
                return new ResponseEntity<String>("Mot de passe incorrect.", HttpStatus.UNAUTHORIZED);
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);
            
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(
                new JwtResponse(
                                jwt,
                                userDetails.getId(),
                                userDetails.getTheUserName(),
                                userDetails.getUsername()
                )
            );
        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la connexion, veuillez réessayer.", status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Échec de la connexion, veuillez réessayer."));
        }
    }

    public ResponseEntity<?> register(SignupRequest signUpRequest) {

        Matcher matcher = emailFormatChecker(signUpRequest.getEmail());
        if (matcher.matches() == false)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Format email invalide."));

        HttpStatus status;
        try {
            if (userServiceImpl.existsByEmail(signUpRequest.getEmail()))
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Un compte existe déjà avec cette adresse email !"));
                     
        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Impossible de vérifier l'email.", status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        
        User user = new User();
        
        user.setEmail(signUpRequest.getEmail());
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        try {
            userServiceImpl.create(user);
            
        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Impossible de créer le compte.", status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new MessageResponse("Compte créé avec succès!"));
    }

    public ResponseEntity<?> getUserApp(UserDetails userDetails) {

        User userApp = new User();
        HttpStatus status;
        
        try {
            userApp = userServiceImpl.findByEmail(userDetails.getUsername()); 

        } catch (HttpStatusCodeException exception) {
            status = exception.getStatusCode();
            return new ResponseEntity<String>("Échec de la récupération de l'utilisateur.", status);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } 
        return new ResponseEntity<User>(userApp, HttpStatus.OK);
    }

    public Matcher emailFormatChecker(String email) {
        
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return matcher;
    }    
}
