package com.project.se.controller;

import com.project.se.domain.ApplicationUser;
import com.project.se.dto.LoginRequestDTO;
import com.project.se.dto.SignUpRequest;
import com.project.se.repository.UserRepository;
import com.project.se.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getName(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> resigterNewUser(@Valid @RequestBody SignUpRequest signUpRequest){
        HashMap<String, Boolean> result = new HashMap<>();
        if (userRepository.existsByName(signUpRequest.getName())){
            result.put("meta", false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            result.put("meta", false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        ApplicationUser user = new ApplicationUser(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        result.put("meta", true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/check")
    public boolean isAuthentication(@RequestHeader(name = "Authorization") String token) {
        String jwt = token.substring(7);
        return jwtTokenProvider.validateToken(jwt);
    }
}
