package com.example.kameleoon.service;

import com.example.kameleoon.model.request.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void createUser(SignUpRequest request);
}
