package com.example.Speer.controller;

import com.example.Speer.Service.UserService.UserInfoService;
import com.example.Speer.entity.UserInfo;
import com.example.Speer.repository.User.UserInfoRepository;
import com.example.Speer.request.user.UserLoginRequest;
import com.example.Speer.request.user.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserInfoRepository userInfoRepository;

    // Endpoint for user registration
    @GetMapping("/signup")
    public ResponseEntity<?> RegisterUserDetails(@Validated @RequestBody UserRegisterRequest userRegisterRequest,
                                                 HttpServletRequest request) {
        // Check if the user with the given username already exists
        if (userInfoRepository.existsByUserName(userRegisterRequest.getUserName()))
            return ResponseEntity.badRequest().body("User already Registered!");

        // Register the user details
        userInfoService.registerUserDetails(userRegisterRequest);
        return ResponseEntity.ok("User Registered Successfully!");
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> AddNoteDetails(@Validated @RequestBody UserLoginRequest userLoginRequest,
                                            HttpServletRequest request) {
        // Retrieve the logged-in user's information
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());

        // Check if the user is found
        if (optionalUserInfo.isEmpty())
            return ResponseEntity.badRequest().body("User Not found, Please Try again!");

        // Perform user login and return the result
        return ResponseEntity.ok(userInfoService.loginUser(userLoginRequest));
    }
}
