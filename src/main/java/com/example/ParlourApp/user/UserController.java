package com.example.ParlourApp.user;

import com.example.ParlourApp.dto.ForgotPasswordRequest;
import com.example.ParlourApp.jwt.JwtUtil;
import com.example.ParlourApp.parlour.EmailService;
import com.example.ParlourApp.parlour.OtpService;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    OtpService otpService;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/UserReg")
    public ResponseEntity<?> register(@RequestBody UserRegModel userRegModel) {
        UserRegModel registeredUser = userService.registerUser(userRegModel.getFullName(), userRegModel.getGender(), userRegModel.getPassword(), userRegModel.getEmail(), userRegModel.getPhoneNumber());
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

    @GetMapping("/userLocation")
    public ResponseEntity<List<ParlourRegModel>> useLocation(@RequestParam double latitude, @RequestParam double longitude) {
        {
            List<ParlourRegModel> nearbyParlours = userService.findNearbyParlours(latitude, longitude, 10.0);

            if (nearbyParlours.isEmpty()) {
                return ResponseEntity.ok(nearbyParlours);
            }

            return ResponseEntity.ok(nearbyParlours);
        }
    }
    @PostMapping("generate-Otp-for-User")
    public ResponseEntity<Map<String, Object>> generateOtpForUsr(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP Send Successfully");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/forgotPasswordUser")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        String newPassword = request.getNewPassword();
        if (!otpService.validateOtp(email, otp)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid or expired OTP");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        Optional<UserRegModel> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserRegModel user = optionalUser.get();

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            otpService.clearOtp(email);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Password reset successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }
}



