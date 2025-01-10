package com.example.ParlourApp.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequestDto
{
    private Long userId;
    private String userName;
    private Long itemId;
    private String itemName;
    private Long parlourId;
    private String parlourName;
    private Long employeeId;
    private String employeeName;
    private Long categoryId;
    private String categoryName;
    private Long subCategoryId;
    private String subCategoryName;
    private Long subSubCategoryId;
    private String subSubCategoryName;
    private LocalDate bookingDate;
    private LocalTime bookingTime;

}
