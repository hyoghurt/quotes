package com.example.kameleoon.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class SignUpRequest {
    private String username;
    private String password;
}
