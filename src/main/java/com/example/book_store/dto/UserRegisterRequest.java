package com.example.book_store.dto;

import com.example.book_store.entity.Role;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}