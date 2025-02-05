package com.example.ParlourApp.employee;

import com.example.ParlourApp.dto.EmployeeDto;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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


    public ResponseEntity<List<EmployeeDto>> getEmployeesByParlourName(String parlourName) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<EmployeeRegModel> employeeRegModelList = employeeRepository.findEmployeesByParlourName(parlourName);
        if (!employeeRegModelList.isEmpty()){
            for (EmployeeRegModel employeeRegModel :employeeRegModelList){
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId(employeeRegModel.getId());
                employeeDto.setEmployeeName(employeeRegModel.getEmployeeName());
                employeeDto.setImage(employeeRegModel.getImage());
                employeeDto.setIsAvailable(employeeRegModel.getIsAvailable());
                employeeDtoList.add(employeeDto);
            }
            return new ResponseEntity<>(employeeDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<EmployeeDto>>getEmployeeByParlourId(Long parlourId)
    {
        List<EmployeeDto>employeeDtoList=new ArrayList<>();
        List<EmployeeRegModel>employeeRegModelList=employeeRepository.findEmployeeByParlourId(parlourId);
        if (!employeeRegModelList.isEmpty()){
            for (EmployeeRegModel employeeRegModel:employeeRegModelList)
            {
                EmployeeDto employeeDto=new EmployeeDto();
                employeeDto.setId(employeeRegModel.getId());
                employeeDto.setEmployeeName(employeeRegModel.getEmployeeName());
                employeeDto.setImage(employeeRegModel.getImage());
                employeeDto.setIsAvailable(employeeRegModel.getIsAvailable());
                employeeDtoList.add(employeeDto);
            }
            return new ResponseEntity<>(employeeDtoList,HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
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

