package com.example.ParlourApp.dto;

import lombok.Data;

@Data
public class EmployeeDto
{
    private Long id;
    private String employeeName;
    private byte[] image;
    private Boolean isAvailable;

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

}
