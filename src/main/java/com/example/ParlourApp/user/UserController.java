package com.example.ParlourApp.user;

import com.example.ParlourApp.jwt.JwtUtil;
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
    @Autowired
    ParlourRepository parlourRepository;

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

    @GetMapping("/location")
    public ResponseEntity<String> receiveLocation(@RequestParam double latitude, @RequestParam double longitude, HttpSession session) {
        session.setAttribute("latitude", latitude);
        session.setAttribute("longitude", longitude);
        System.out.println("Location saved :Latitude= " + latitude + ",Longitude= " + longitude);
        return ResponseEntity.ok("Location saved in session .");

    }

    @GetMapping("/userLocation")
    public ResponseEntity<List<ParlourRegModel>> useLocation(HttpSession session) {
        Double latitude = (Double) session.getAttribute("latitude");
        Double longitude = (Double) session.getAttribute("longitude");

        if (latitude != null && longitude != null) {
            List<ParlourRegModel> nearbyParlours = userService.findNearbyParlours(latitude, longitude, 10.0);

            if (nearbyParlours.isEmpty()) {
                return ResponseEntity.ok(nearbyParlours);
            }

            return ResponseEntity.ok(nearbyParlours);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}