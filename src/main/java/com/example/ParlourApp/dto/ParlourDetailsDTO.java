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
}
