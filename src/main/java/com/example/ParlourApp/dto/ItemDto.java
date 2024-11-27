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
    private Long CategoryId;
    private String CategoryName;
    private byte[] categoryImage;
    private Long SubCategoryId;
    private String SubCategoryName;
    private byte[] subCategoryImage;
    private Long SubSubCategoryId;
    private String SubSubCategoryName;
    private byte[] subSubCategoryImage;
    private BigDecimal price;
    private Boolean Availability;
    private String Description;
    private LocalTime ServiceTime;

}
