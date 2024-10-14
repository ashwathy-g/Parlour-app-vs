package com.example.ParlourApp.parlour;

import com.example.ParlourApp.OfferCategory.OfferCategoryRegModel;
import com.example.ParlourApp.OfferCategory.OfferCategoryRepository;
import com.example.ParlourApp.Offers.OfferRegModel;
import com.example.ParlourApp.Offers.OfferRepository;
import com.example.ParlourApp.admin.AdminRegModel;
import com.example.ParlourApp.category.CategoryRegModel;
import com.example.ParlourApp.dto.*;
import com.example.ParlourApp.employee.EmployeeRegModel;
import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.items.ItemRegModel;
import com.example.ParlourApp.items.ItemRepository;
import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtUtil;
import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import com.example.ParlourApp.subsubcategory.SubSubCategoryRegModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@Service
@Slf4j
public class ParlourService {
    @Autowired
    private ParlourRepository parlourRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    OfferCategoryRepository offerCategoryRepository;
    @Autowired
    EmailService emailService;


    public ParlourRegModel registerParlour(ParlourRegModel parlourRegModel) {

        if (parlourRepository.findByEmail(parlourRegModel.getEmail()).isPresent())
        {
            throw new RuntimeException("Email already exists.Please use a different email.");
        }
        if (parlourRepository.findByPhoneNumber(parlourRegModel.getPhoneNumber()).isPresent())
        {
            throw new RuntimeException("Phone number already exists.Please use a different phone number.");
        }
        parlourRegModel.setPassword(passwordEncoder.encode(parlourRegModel.getPassword()));
        parlourRegModel.getRoles().add("ROLE_PARLOUR");
        parlourRegModel.setStatus(0);
        return parlourRepository.save(parlourRegModel);
    }

    public String authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        } else {
            return null;
        }
    }


    public ParlourRegModel getParlourByEmailAndPassword(String email, String password) {
        return parlourRepository.findByEmailAndPassword(email, password);
    }

    public ParlourRegModel getParlourById(Long id) {
        return parlourRepository.findById(id).orElseThrow(() -> new RuntimeException("Parlour not found with id: " + id));
    }

    public boolean updateParlour(Long id, ParlourRegModel parlourDetails) {
        Optional<ParlourRegModel> optionalParlour = parlourRepository.findById(id);

        if (optionalParlour.isPresent()) {
            ParlourRegModel existingParlour = optionalParlour.get();

            existingParlour.setParlourName(parlourDetails.getParlourName());
            existingParlour.setPhoneNumber(parlourDetails.getPhoneNumber());
            existingParlour.setPassword(parlourDetails.getPassword());
            existingParlour.setEmail(parlourDetails.getEmail());
            if (parlourDetails.getImage() != null) {
                existingParlour.setImage(parlourDetails.getImage());
            }
            existingParlour.setLicenseNumber(parlourDetails.getLicenseNumber());

            if (parlourDetails.getLicenseImage() != null) {
                existingParlour.setLicenseImage(parlourDetails.getLicenseImage());
            }
            existingParlour.setRatings(parlourDetails.getRatings());
            existingParlour.setLocation(parlourDetails.getLocation());
            existingParlour.setLatitude(parlourDetails.getLatitude());
            existingParlour.setLongitude(parlourDetails.getLongitude());
            existingParlour.setDescription(parlourDetails.getDescription());
            existingParlour.setStatus(0);

            parlourRepository.save(existingParlour);
            return true;
        } else {
            return false;
        }
    }

    public ParlourRegModel getParlourByName(String parlourName) {
        return parlourRepository.findByParlourName(parlourName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parlour name"));
    }

    public List<EmployeeRegModel> getEmployeesByParlourId(Long parlourId) {
        return employeeRepository.findByParlourId_Id(parlourId);
    }

    public ParlourDetails getParlourDetails(String parlourName) {
        ParlourRegModel parlour = getParlourByName(parlourName);
        List<EmployeeRegModel> employees = getEmployeesByParlourId(parlour.getId());
        return new ParlourDetails(parlour.getParlourName(), parlour.getPhoneNumber(), parlour.getEmail(), employees);
    }

    public List<ParlourRegModel> getAllParlours() {
        return parlourRepository.findAll();

    }

    public ResponseEntity<List<ParlourDetailsDTO>> getParlourDetailsById(Long id) {
        List<ParlourDetailsDTO> parlourDetailsDTOList = new ArrayList<>();
        Optional<ParlourRegModel> parlourRegModelOptional = parlourRepository.findById(id);
        if (parlourRegModelOptional.isPresent()) {
            ParlourRegModel parlourRegModel = parlourRegModelOptional.get();
            ParlourDetailsDTO parlourDetailsDTO = new ParlourDetailsDTO();
            parlourDetailsDTO.setParlourName(parlourRegModel.getParlourName());
            parlourDetailsDTO.setPhoneNumber(parlourRegModel.getPhoneNumber());
            parlourDetailsDTO.setEmail(parlourRegModel.getEmail());
            parlourDetailsDTO.setImage(parlourRegModel.getImage());
            parlourDetailsDTO.setCoverImage(parlourRegModel.getCoverImage());
            parlourDetailsDTO.setRatings(parlourRegModel.getRatings());
            parlourDetailsDTO.setDescription(parlourRegModel.getDescription());
            parlourDetailsDTO.setStatus(parlourRegModel.getStatus());
            parlourDetailsDTO.setLocation(parlourRegModel.getLocation());

            List<EmployeeRegModel> employeeRegModelOptional = employeeRepository.findByParlourId_Id(id);
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            if (!employeeRegModelOptional.isEmpty()) {
                for (EmployeeRegModel employeeRegModel : employeeRegModelOptional) {
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employeeRegModel.getId());
                    employeeDto.setEmployeeName(employeeRegModel.getEmployeeName());
                    employeeDto.setImage(employeeRegModel.getImage());
                    employeeDto.setAvailable(employeeRegModel.getIsAvailable());
                    employeeDtoList.add(employeeDto);
                }
                parlourDetailsDTO.setEmployees(employeeDtoList);
            }

            List<ItemRegModel> itemRegModelList = itemRepository.findByParlourId_Id(id);
            List<ItemDto> itemDtoList = new ArrayList<>();
            if (!itemRegModelList.isEmpty()) {
                for (ItemRegModel itemRegModel : itemRegModelList) {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setId(itemRegModel.getId());
                    itemDto.setItemName(itemRegModel.getItemName());
                    itemDto.setItemImage(itemRegModel.getItemImage());
                    itemDto.setPrice(itemRegModel.getPrice());
                    itemDto.setAvailability(itemRegModel.getAvailability());
                    itemDto.setServiceTime(itemRegModel.getServiceTime());
                    itemDto.setDescription(itemRegModel.getDescription());
                    CategoryRegModel category = itemRegModel.getCategory();
                    SubCategoryRegModel subCategory = itemRegModel.getSubCategory();
                    SubSubCategoryRegModel subSubCategory = itemRegModel.getSubSubCategory();
                    itemDto.setCategoryId(itemRegModel.getCategoryId());
                    itemDto.setCategoryName(category.getName());
                    itemDto.setCategoryImage(category.getImage());
                    itemDto.setSubCategoryId(itemRegModel.getSubCategoryId());
                    itemDto.setSubCategoryName(subCategory.getName());
                    itemDto.setSubCategoryImage(subCategory.getImage());
                    itemDto.setSubSubCategoryId(itemRegModel.getSubSubCategoryId());
                    itemDto.setSubSubCategoryName(subSubCategory.getName());
                    itemDto.setSubSubCategoryImage(subSubCategory.getImage());
                    itemDtoList.add(itemDto);

                }
                parlourDetailsDTO.setItems(itemDtoList);
            }
            List<OfferRegModel> offerList = offerRepository.findByParlourId(id);
            if (!offerList.isEmpty()) {
                parlourDetailsDTO.setOffers(offerList);
            } else {
                parlourDetailsDTO.setOffers(null);  // No offers found
            }
            List<OfferCategoryRegModel> offerCategoryList = offerCategoryRepository.findByOfferId(id);
            if (!offerCategoryList.isEmpty()) {
                parlourDetailsDTO.setOfferCategories(offerCategoryList);
            } else {
                parlourDetailsDTO.setOfferCategories(null);  // No category-specific offers found
            }


            parlourDetailsDTOList.add(parlourDetailsDTO);
        }
        return new ResponseEntity<>(parlourDetailsDTOList, HttpStatus.OK);
    }

    public ResponseEntity<List<OfferDto>> getOffersByParlourId(Long parlourId) {
        List<OfferDto> offerDtoList = new ArrayList<>();
        List<OfferRegModel> offerRegModelList = offerRepository.findByParlourId(parlourId);
        for (OfferRegModel offerRegModel : offerRegModelList) {
            OfferDto offerDto = new OfferDto();
            offerDto.setOfferId(offerRegModel.getId());
            offerDto.setOfferName(offerRegModel.getName());
            offerDto.setOfferDescription(offerRegModel.getDescription());
            List<OfferCategoryRegModel> offerCategoryRegModelList = offerCategoryRepository.findByOfferId(offerRegModel.getId());
            List<OfferCategoryDto> offerCategoryDtoList = new ArrayList<>();
            for (OfferCategoryRegModel offerCategoryRegModel : offerCategoryRegModelList) {
                OfferCategoryDto offerCategoryDto = new OfferCategoryDto();
                offerCategoryDto.setCategoryId(offerCategoryRegModel.getCategoryId());
                offerCategoryDto.setCategoryName(offerCategoryRegModel.getCategoryName());
                offerCategoryDto.setOfferPrice(offerCategoryRegModel.getOfferPrice());
                offerCategoryDto.setDescription(offerCategoryRegModel.getDescription());
                offerCategoryDto.setImage(offerCategoryRegModel.getImage());
                offerCategoryDtoList.add(offerCategoryDto);
            }
            offerDto.setOfferCategories(offerCategoryDtoList);


            List<ItemRegModel> itemRegModelList = itemRepository.findByParlourId_Id(parlourId);
            if (itemRegModelList.isEmpty()) {
                System.out.println("No items found for parlourId: " + parlourId);
            } else {
                System.out.println("Items found: " + itemRegModelList.size());
            }

            List<ItemDto> itemDtoList = new ArrayList<>();
            for (ItemRegModel itemRegModel : itemRegModelList) {
                ItemDto itemDto = new ItemDto();
                itemDto.setId(itemRegModel.getId());
                itemDto.setItemName(itemRegModel.getItemName());
                itemDto.setItemImage(itemRegModel.getItemImage());
                itemDto.setPrice(itemRegModel.getPrice());
                itemDto.setAvailability(itemRegModel.getAvailability());
                itemDto.setServiceTime(itemRegModel.getServiceTime());
                itemDto.setDescription(itemRegModel.getDescription());
                if (itemRegModel.getCategory() != null) {
                    itemDto.setCategoryId(itemRegModel.getCategory().getId());
                    itemDto.setCategoryName(itemRegModel.getCategory().getName());
                    itemDto.setCategoryImage(itemRegModel.getCategory().getImage());

                }
                if (itemRegModel.getSubCategory() != null) {
                    itemDto.setSubCategoryId(itemRegModel.getSubCategory().getId());
                    itemDto.setSubCategoryName(itemRegModel.getSubCategory().getName());
                    itemDto.setSubCategoryImage(itemRegModel.getSubCategory().getImage());
                }
                if (itemRegModel.getSubSubCategory() != null) {
                    itemDto.setSubSubCategoryId(itemRegModel.getSubSubCategory().getId());
                    itemDto.setSubSubCategoryName(itemRegModel.getSubSubCategory().getName());
                    itemDto.setSubSubCategoryImage(itemRegModel.getSubSubCategory().getImage());
                }

                itemDtoList.add(itemDto);
            }
            offerDto.setItems(itemDtoList);

            offerDtoList.add(offerDto);
        }

        return new ResponseEntity<>(offerDtoList, HttpStatus.OK);
    }


    public void deleteParlourById(Long id) {
        if (parlourRepository.existsById(id)) {
            parlourRepository.deleteById(id);
        } else {
            throw new RuntimeException("Parlour with ID" + id + "not found .");
        }
    }

}