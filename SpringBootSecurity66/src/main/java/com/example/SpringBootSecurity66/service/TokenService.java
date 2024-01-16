package com.example.SpringBootSecurity66.service;

import com.example.SpringBootSecurity66.model.Token;
import com.example.SpringBootSecurity66.model.User;
import com.example.SpringBootSecurity66.model.UserPrincipal;
import com.example.SpringBootSecurity66.repository.ITokenRepo;
import com.example.SpringBootSecurity66.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class TokenService implements UserDetailsService {

    IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.SpringBootSecurity66.model.User user= userRepository.findByEmail(username);

        if (user==null){
            throw new UsernameNotFoundException("No user forund with this user ID");
        }
        return new UserPrincipal(user);
    }
}
