package com.example.Speer.Service.UserService;

import com.example.Speer.Security.jwt.JwtUtils;
import com.example.Speer.entity.UserInfo;
import com.example.Speer.repository.User.UserInfoRepository;
import com.example.Speer.request.user.UserLoginRequest;
import com.example.Speer.request.user.UserRegisterRequest;
import com.example.Speer.response.user.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserInfoService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    // Method to register user details
    public void registerUserDetails(UserRegisterRequest userRegisterRequest) {
        // Create a new UserInfo instance and set its properties from the request
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(userRegisterRequest.getFullName());
        userInfo.setUserName(userRegisterRequest.getUserName());

        // Decode the Base64-encoded password and encode it with the password encoder
        byte[] decodedBytes = Base64.getDecoder().decode(userRegisterRequest.getPassword().getBytes());
        String decodedPasswordString = new String(decodedBytes);
        userInfo.setPassword(encoder.encode(decodedPasswordString));

        // Save the user information to the repository
        try {
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving User Information!");
        }
    }

    // Method to perform user login and generate JWT token
    public JwtResponse loginUser(UserLoginRequest userLoginRequest) {
        // Decode the Base64-encoded password
        byte[] decodedBytes = Base64.getDecoder().decode(userLoginRequest.getPassword().getBytes());
        String decodedPasswordString = new String(decodedBytes);

        // Authenticate the user using the authentication manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), decodedPasswordString));

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token and create JwtResponse
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new JwtResponse(jwt, userLoginRequest.getUserName());
    }
}
