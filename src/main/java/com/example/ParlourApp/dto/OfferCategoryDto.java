package com.example.ParlourApp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OfferCategoryDto
{
    private Long categoryId;
    private String categoryName;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer offerPrice;
    private String description;
    private byte[] image;

}
