package com.example.ParlourApp.dto;

import com.example.ParlourApp.employee.EmployeeRegModel;
import com.example.ParlourApp.items.ItemRegModel;
import lombok.Data;

import java.util.List;

@Data
public class ParlourDetails
{
    private String parlourName;
    private String phoneNumber;
    private String email;
    private List<EmployeeRegModel> employees;
    private List<ItemRegModel>items;

    public ParlourDetails(String parlourName, String phoneNumber, String email, List<EmployeeRegModel> employees,List<ItemRegModel>items)
    {
        this.parlourName = parlourName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employees = employees;
        this.items=items;
    }
}
