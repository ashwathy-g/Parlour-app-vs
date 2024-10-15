package com.example.ParlourApp.user;

import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ParlourRepository parlourRepository;


    @Autowired
    PasswordEncoder passwordEncoder;
    public UserService(ParlourRepository parlourRepository)
    {
        this.parlourRepository=parlourRepository;
    }

    public UserRegModel registerUser(String fullName, String gender, String password, String email, String phoneNumber) {
        if (fullName == null || gender == null || password == null || email == null || phoneNumber == null) {
            return null;
        }
        Optional<UserRegModel> existingUserByPhone = userRepository.findByPhoneNumber(phoneNumber);
        Optional<UserRegModel> existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByPhone.isPresent()) {
            System.out.println("User already exists with phone number: " + phoneNumber);
            return null;
        }

        if (existingUserByEmail.isPresent()) {
            System.out.println("User already exists with email: " + email);
            return null;
        }



        UserRegModel userRegModel = new UserRegModel();
        userRegModel.setFullName(fullName);
        userRegModel.setGender(gender);
        userRegModel.setPassword(passwordEncoder.encode(password));
        userRegModel.setEmail(email);
        userRegModel.setPhoneNumber(phoneNumber);
        userRegModel.setRoles(Arrays.asList("ROLE_USER"));
        return userRepository.save(userRegModel);
    }
    public boolean isUserExistsByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
    public boolean isUserExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public UserRegModel authenticate(String phoneNumber, String password) {
        Optional<UserRegModel> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isPresent()) {
            UserRegModel user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    public List<ParlourRegModel> findNearbyParlours(double userLatitude, double userLongitude, double radiusInKm) {
        List<ParlourRegModel> allParlours = parlourRepository.findAll();
        return allParlours.stream().filter(parlourRegModel -> parlourRegModel.getLatitude() != null && parlourRegModel.getLongitude() != null)
                .filter(parlourRegModel -> {

                    double distance = calculateDistance(userLatitude, userLongitude, parlourRegModel.getLatitude(), parlourRegModel.getLongitude());
                    return distance <= radiusInKm;
                }).collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius of Earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Distance in kilometers
    }
}




