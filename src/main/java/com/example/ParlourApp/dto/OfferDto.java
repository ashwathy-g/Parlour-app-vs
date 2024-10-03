package com.example.ParlourApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class OfferDto
{
    private Long offerId;
    private String offerName;
    private String offerDescription;
    private List<OfferCategoryDto> offerCategories;
    private List<ItemDto> items;
}
