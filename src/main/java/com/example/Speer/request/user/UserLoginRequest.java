package com.example.Speer.request.user;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
