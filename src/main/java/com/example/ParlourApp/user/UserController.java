package com.example.ParlourApp.user;

import com.example.ParlourApp.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;


    @PostMapping(path = "/UserReg")
    public ResponseEntity<?> register(@RequestBody UserRegModel userRegModel) {
        UserRegModel registeredUser = userService.registerUser(userRegModel.getFullName(),  userRegModel.getGender(), userRegModel.getPassword(), userRegModel.getEmail(), userRegModel.getPhoneNumber());
        if (registeredUser == null) {
            // Check if the reason for failure is a duplicate phone number or email
            if (userService.isUserExistsByPhoneNumber(userRegModel.getPhoneNumber())) {
                return ResponseEntity.badRequest().body("A user with this phone number already exists.");
            } else if (userService.isUserExistsByEmail(userRegModel.getEmail())) {
                return ResponseEntity.badRequest().body("A user with this email already exists.");
            } else {
                return ResponseEntity.badRequest().body("Registration failed. Invalid input data.");
            }
        }
        return ResponseEntity.ok("User registered successfully.");
    }





        @PostMapping(path = "/UserLogin")
    public ResponseEntity<?> login(@RequestBody UserRegModel userRegModel) {
        UserRegModel authenticatedUser = userService.authenticate(userRegModel.getPhoneNumber(), userRegModel.getPassword());
        if (authenticatedUser != null) {
            List<GrantedAuthority> authorities = authenticatedUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            UserDetails userDetails = new User(authenticatedUser.getPhoneNumber(), authenticatedUser.getPassword(), authorities);
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        }
    }
}

