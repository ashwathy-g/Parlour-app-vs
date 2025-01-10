package com.example.ParlourApp.user;

import com.example.ParlourApp.OfferCategory.OfferCategoryRegModel;
import com.example.ParlourApp.OfferCategory.OfferCategoryRepository;
import com.example.ParlourApp.Offers.OfferRegModel;
import com.example.ParlourApp.Offers.OfferRepository;
import com.example.ParlourApp.dto.EmployeeDto;
import com.example.ParlourApp.dto.ItemDto;
import com.example.ParlourApp.dto.ParlourDetailsDTO;
import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.items.ItemRepository;
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
    PasswordEncoder passwordEncoder;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ParlourRepository parlourRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    OfferCategoryRepository offerCategoryRepository;

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
//public List<ParlourDetailsDTO> findNearbyParlours(double latitude, double longitude, double radius) {
//    // Get nearby parlours based on location
//    List<ParlourRegModel> nearbyParlours = parlourRepository.findNearbyParlours(latitude, longitude, radius);
//
//    // Convert each ParlourRegModel to ParlourDetailsDTO
//    return nearbyParlours.stream()
//            .map(parlour -> {
//                List<EmployeeDto> employees = getEmployeesByParlourId(parlour.getId());
//                List<ItemDto> items = getItemsByParlourId(parlour.getId());
//                List<OfferRegModel> offers = getOffersByParlourId(parlour.getId());
//                List<OfferCategoryRegModel> offerCategories = getOfferCategoriesByParlourId(parlour.getId());
//
//                return new ParlourDetailsDTO(
//                        parlour.getParlourName(),
//                        parlour.getPhoneNumber(),
//                        parlour.getEmail(),
//                        parlour.getImage(),
//                        parlour.getCoverImage(),
//                        parlour.getRatings(),
//                        parlour.getLocation(),
//                        parlour.getDescription(),
//                        parlour.getStatus(),
//                        employees,
//                        items,
//                        offers,
//                        offerCategories
//                );
//            })
//            .collect(Collectors.toList());
//}
//
//    // Helper methods to fetch related data
//    private List<EmployeeDto> getEmployeesByParlourId(Long parlourId) {
//        return employeeRepository.findByParlourId(parlourId).stream()
//                .map(employee -> new EmployeeDto(employee.getId(), employee.getEmployeeName(), employee.getImage(),employee.getIsAvailable()))
//                .collect(Collectors.toList());
//    }
//
//    private List<ItemDto> getItemsByParlourId(Long parlourId) {
//        return itemRepository.findByParlourId(parlourId).stream()
//                .map(item -> {
//                    ItemDto dto = new ItemDto();
//                    dto.setId(item.getId());
//                    dto.setItemName(item.getItemName());
//                    dto.setItemImage(item.getItemImage());
//                    if (item.getCategory()!=null)
//                    {
//                        dto.setCategoryId(item.getCategory().getId());
//                        dto.setCategoryName(item.getCategory().getName());
//                    }
//
//                    if (item.getSubCategory()!=null)
//                    {
//                        dto.setSubCategoryId(item.getSubCategory().getId());
//                        dto.setSubCategoryName(item.getSubCategory().getName());
//                        if (item.getSubSubCategory() != null) {
//                        dto.setSubSubCategoryId(item.getSubSubCategory().getId());
//                        dto.setSubSubCategoryName(item.getSubSubCategory().getName());
//                    }
//                    }
//                    dto.setPrice(item.getPrice());
//                    dto.setAvailability(item.getAvailability());
//                    dto.setDescription(item.getDescription());
//                    dto.setServiceTime(item.getServiceTime());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    private List<OfferRegModel> getOffersByParlourId(Long parlourId) {
//        return offerRepository.findByParlourId(parlourId);
//    }
//
//    private List<OfferCategoryRegModel> getOfferCategoriesByParlourId(Long parlourId) {
//        return offerCategoryRepository.findByParlourId(parlourId);
//    }