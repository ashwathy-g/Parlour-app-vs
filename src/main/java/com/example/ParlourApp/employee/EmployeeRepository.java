package com.example.ParlourApp.employee;

import com.example.ParlourApp.dto.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeRegModel,Long>
{
//    @Query("SELECT new com.example.ParlourApp.dto.EmployeeDto(e.id,e.employeeName,e.image,e.isAvailable) "+ "FROM EmployeeRegModel e WHERE e.parlour.parlourName=:parlourName")
//

//List<EmployeeDto> findEmployeesByParlourName(@Param( "parlourName") String parlourName);

    Optional<EmployeeRegModel> findByEmployeeName(String employeeName);

    List<EmployeeRegModel> findByParlourId_Id(Long parlourId);



    @Query("SELECT e FROM EmployeeRegModel e WHERE e.parlour.id = :parlourId")
    List<EmployeeRegModel> findByParlourId(@Param("parlourId") Long parlourId);

    @Query("SELECT e FROM EmployeeRegModel e WHERE e.parlour.parlourName = :parlourName")
    List<EmployeeRegModel> findEmployeesByParlourName(String parlourName);

    List<EmployeeRegModel> findEmployeeByParlourId(Long parlourId);
}
