package com.example.kameleoon.service;

import com.example.kameleoon.exception.UserBadRequestException;
import com.example.kameleoon.model.UserDetailsImpl;
import com.example.kameleoon.model.entity.UserEntity;
import com.example.kameleoon.model.request.SignUpRequest;
import com.example.kameleoon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(userEntity);
    }

    @Override
    public void createUser(SignUpRequest request) {
        userRepository.findById(request.getUsername()).ifPresent((e) -> {
            throw new UserBadRequestException("user already exists: " + request.getUsername());
        });

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        jdbcAggregateTemplate.insert(user);
    }
}
