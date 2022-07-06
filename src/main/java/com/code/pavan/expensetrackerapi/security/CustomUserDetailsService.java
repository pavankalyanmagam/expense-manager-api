package com.code.pavan.expensetrackerapi.security;

import com.code.pavan.expensetrackerapi.entity.User;
import com.code.pavan.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User existingUserRepository =userRepository
                .findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email "));
        return new org.springframework.security.core.userdetails.User(existingUserRepository.getEmail(),existingUserRepository.getPassword(), new ArrayList<>());

    }
}
