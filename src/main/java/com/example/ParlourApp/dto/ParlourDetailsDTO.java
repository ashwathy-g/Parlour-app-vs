package com.example.ParlourApp.dto;

import com.example.ParlourApp.OfferCategory.OfferCategoryRegModel;
import com.example.ParlourApp.Offers.OfferRegModel;
import lombok.Data;

import java.util.List;

@Data
public class ParlourDetailsDTO
{
    private String parlourName;
    private String phoneNumber;
    private String email;
    private byte[] image;
    private byte[] coverImage;
    private Integer ratings;
    private String location;
    private String description;
    private Integer status;
    private List<EmployeeDto> employees;
    private List<ItemDto> items;
    private List<OfferRegModel> offers;
    private List<OfferCategoryRegModel> offerCategories;
    public ParlourDetailsDTO() {
    }

    public ParlourDetailsDTO(
            String parlourName,
            String phoneNumber,
            String email,
            byte[] image,
            byte[] coverImage,
            Integer ratings,
            String location,
            String description,
            Integer status,
            List<EmployeeDto> employees,
            List<ItemDto> items,
            List<OfferRegModel> offers,
            List<OfferCategoryRegModel> offerCategories
    ) {
        this.parlourName = parlourName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.coverImage = coverImage;
        this.ratings = ratings;
        this.location = location;
        this.description = description;
        this.status = status;
        this.employees = employees;
        this.items = items;
        this.offers = offers;
        this.offerCategories = offerCategories;
    }
}

