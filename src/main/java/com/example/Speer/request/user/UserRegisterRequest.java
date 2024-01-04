package com.example.Speer.request.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String userName;
    private String fullName;
    private String password;
}
