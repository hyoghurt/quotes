package com.example.kameleoon.service;

import com.example.kameleoon.model.request.SignUpRequest;

public interface UserService {
    void createUser(SignUpRequest request);
}
