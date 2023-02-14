package com.example.kameleoon.service;

import com.example.kameleoon.exception.UserBadRequestException;
import com.example.kameleoon.model.request.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;

    @Override
    public void createUser(SignUpRequest request) {

        if (userDetailsManager.userExists(request.getUsername())) {
            throw new UserBadRequestException("user already exists: " + request.getUsername());
        }

        UserDetails user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);
    }
}
