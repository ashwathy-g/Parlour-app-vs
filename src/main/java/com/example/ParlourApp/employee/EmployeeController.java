package com.example.ParlourApp.employee;

import com.example.ParlourApp.dto.EmployeeDto;
import com.example.ParlourApp.items.ItemRegModel;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ParlourRepository parlourRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @PreAuthorize("hasAuthority('ROLE_PARLOUR')")
    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(
            @RequestParam("employeeName") String employeeName,
            @RequestParam("parlourId") Long parlourId,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            ParlourRegModel parlour = parlourRepository.findById(parlourId)
                    .orElseThrow(() -> new RuntimeException("Parlour not found with id: " + parlourId));

            EmployeeRegModel employee = new EmployeeRegModel();
            employee.setEmployeeName(employeeName);
            employee.setParlour(parlour);
            employee.setImage(image != null ? image.getBytes() : null);
            employeeRepository.save(employee);

            return ResponseEntity.ok("Employee added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding employee: " + e.getMessage());
        }
    }

//    public ResponseEntity<EmployeeRegModel> addEmployee(@RequestParam String employeeName,
//                                                        @RequestParam Long parlourId,
//                                                        @RequestParam MultipartFile image)throws IOException
//    {
//        byte[] imageBytes = image.getBytes();
//        EmployeeRegModel employeeRegModel = employeeService.addEmployee(employeeName, parlourId, imageBytes);
//        return ResponseEntity.ok(employeeRegModel);
//    }

    @GetMapping("/by-parlourName")
//    public ResponseEntity<List<EmployeeDto>> getEmployeeNames(@RequestParam String parlourName) {
//        List<EmployeeDto> employees = employeeService.getEmployeesByParlourName(parlourName);
//        return ResponseEntity.ok(employees);
//    }

    public ResponseEntity<List<EmployeeDto>> getEmployeesByParlourName(@RequestParam String parlourName) {
        return employeeService.getEmployeesByParlourName(parlourName);
    }

    @GetMapping("/by-parlourId")
    public ResponseEntity<List<EmployeeDto>>getEmployeesByParlourId(@RequestParam Long parlourId)
    {
        return employeeService.getEmployeeByParlourId(parlourId);
    }

    @GetMapping("/employeeById")
    public ResponseEntity<EmployeeRegModel>getEmployeeById(@RequestParam Long employeeId)
    {
        Optional<EmployeeRegModel> employee=employeeService.getEmployeeById(employeeId);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<EmployeeRegModel> updateEmployees(@RequestParam Long employeeId,
                                                            @RequestParam String employeeName,
                                                            @RequestParam MultipartFile image) {
        Optional<EmployeeRegModel> updatedEmployee = employeeService.updateEmployee(employeeId, employeeName, image);
        if (updatedEmployee.isPresent()) {
            return ResponseEntity.ok(updatedEmployee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("delete")
        public ResponseEntity<String>deleteEmployee(@RequestParam Long employeeId)
        {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok("Employee deleted Successfully.");
    }

}

