package com.project.se.controller;

import com.project.se.domain.ApplicationUser;
import com.project.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        System.out.println("Go here already");
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        String pass = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        System.out.println(pass);
        System.out.println(applicationUser.getPassword());
        System.out.println(bCryptPasswordEncoder.matches(applicationUser.getPassword(), pass));
        System.out.println(userRepository.findAll());
    }
}
