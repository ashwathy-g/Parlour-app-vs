package com.example.ParlourApp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class ItemDto
{
    private Long id;
    private String ItemName;
    private byte[] ItemImage;
    private String CategoryName;
    private String SubCategoryName;
    private String SubSubCategoryName;
    private BigDecimal price;
    private Boolean Availability;
    private String Description;
    private LocalTime ServiceTime;


}
