package com.project.se.service;

import com.project.se.domain.ApplicationUser;
import com.project.se.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByName(s);
        if (applicationUser == null){
            throw new UsernameNotFoundException(s);
        }
        return new User(applicationUser.getName(), applicationUser.getPassword(), emptyList());
    }

    public UserDetails loadUserById(int id){
        ApplicationUser applicationUser = userRepository.findById(id).orElse(null);
        if (applicationUser == null){
            throw new UsernameNotFoundException("Not found");
        }
        return new User(applicationUser.getName(), applicationUser.getPassword(), emptyList());
    }
}
