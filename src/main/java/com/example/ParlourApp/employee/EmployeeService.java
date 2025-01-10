package com.example.ParlourApp.employee;

import com.example.ParlourApp.dto.EmployeeDto;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@CrossOrigin
public class EmployeeService
{
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ParlourRepository parlourRepository;

    public EmployeeRegModel addEmployee(String employeeName, Long parlourId, byte[] image) {
        ParlourRegModel parlourRegModel = parlourRepository.findById(parlourId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parlour ID"));
        EmployeeRegModel employeeRegModel = new EmployeeRegModel(employeeName, parlourRegModel);
        employeeRegModel.setImage(image);
        log.info("Adding employee: {}", employeeRegModel);
        return employeeRepository.save(employeeRegModel);
    }
   public Optional<EmployeeRegModel>getEmployeeById(Long employeeId)
  {
    return employeeRepository.findById(employeeId);
  }
    public List<EmployeeDto> getEmployeesByParlourName(String parlourName) {
        List<EmployeeDto> employees = employeeRepository.findEmployeesByParlourName(parlourName);
        log.info("Retrieved employee names for parlour '{}': {}", parlourName, employees);
        return employees;
    }
    public Optional<EmployeeRegModel> updateEmployee(Long employeeId, String employeeName, MultipartFile image)
    {
        Optional<EmployeeRegModel> employeeRegModelOptional=employeeRepository.findById(employeeId);
        if (employeeRegModelOptional.isPresent())
        {
            EmployeeRegModel employeeRegModel=employeeRegModelOptional.get();
            employeeRegModel.setEmployeeName(employeeName);
            if (image!=null&&!image.isEmpty())
            {
                try
                {
                    byte[]imageData= image.getBytes();
                    employeeRegModel.setImage(imageData);

                }
                catch (IOException e)
                {
                    throw new RuntimeException("Error while uploading the image",e);
                }
            }
            EmployeeRegModel updatedEmployee=employeeRepository.save(employeeRegModel);
            return Optional.of(updatedEmployee);
        }
        return Optional.empty();
    }
public void deleteEmployee(Long employeeId)
{
    if (!employeeRepository.existsById(employeeId))
    {
        throw new EntityNotFoundException("Employee with ID" + employeeId + "not found .");
    }
    employeeRepository.deleteById(employeeId);
}
}

