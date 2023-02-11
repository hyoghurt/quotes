package com.example.kameleoon.controller;

import com.example.kameleoon.model.request.SignUpRequest;
import com.example.kameleoon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpRequest request) {
        userService.createUser(request);
    }
}
