package com.example.kameleoon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString(exclude = {"password"})
@Table("USERS")
public class UserEntity {
    @Id
    private String username;
    @JsonIgnore
    private String password;
}
