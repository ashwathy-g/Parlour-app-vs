package com.example.ParlourApp.parlour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParlourRepository extends JpaRepository<ParlourRegModel,Long>
{

    ParlourRegModel findByEmailAndPassword(String email, String password);

   Optional<ParlourRegModel>  findByEmail(String email);
    Optional<ParlourRegModel>findById(Long id);

    Optional<ParlourRegModel> findByParlourName(String parlourName);



    @Query("SELECT p FROM ParlourRegModel p WHERE LOWER(p.email) = LOWER(:email)")
    Optional<ParlourRegModel> findByEmailIgnoreCase(@Param("email") String email);

    Optional<ParlourRegModel> findByPhoneNumber(String phoneNumber);
}

