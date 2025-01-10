package com.example.ParlourApp.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@CrossOrigin
public class EmployeeDto
{
    private Long id;
    private String employeeName;
    private byte[] image;
    private Boolean isAvailable;
    public EmployeeDto()
    {

    }

    public EmployeeDto(Long id, String employeeName, byte[] image, Boolean isAvailable) {
        this.id = id;
        this.employeeName = employeeName;
        this.image = image;
        this.isAvailable = isAvailable;
    }
    public EmployeeDto(Long id, String employeeName) {
        this.id = id;
        this.employeeName = employeeName;
    }


    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

}
