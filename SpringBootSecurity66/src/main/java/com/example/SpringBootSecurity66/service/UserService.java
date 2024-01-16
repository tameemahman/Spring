package com.example.SpringBootSecurity66.service;

import com.example.SpringBootSecurity66.repository.IUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private IUserRepository userRepo;


    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isEmailUnique(String email){
        com.example.SpringBootSecurity66.model.User userEmail= userRepo.findByEmail(email);
        return  userEmail==null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.SpringBootSecurity66.model.User user= userRepo.findByEmail(username);

    return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRoles().stream().map(role->role.getName()).toArray(String[]::new))
            .build();

    }
}
