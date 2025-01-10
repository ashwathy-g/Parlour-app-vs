package com.example.ParlourApp.parlour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParlourRepository extends JpaRepository<ParlourRegModel,Long> {

    ParlourRegModel findByEmailAndPassword(String email, String password);

    Optional<ParlourRegModel> findByEmail(String email);

    Optional<ParlourRegModel> findById(Long id);

    Optional<ParlourRegModel> findByParlourName(String parlourName);

    @Query("SELECT p FROM ParlourRegModel p WHERE LOWER(p.email) = LOWER(:email)")
    Optional<ParlourRegModel> findByEmailIgnoreCase(@Param("email") String email);

    Optional<ParlourRegModel> findByPhoneNumber(String phoneNumber);

    List<ParlourRegModel> findByDeletionRequestedTrue();

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);


        @Query("SELECT p FROM ParlourRegModel p WHERE FUNCTION('distance', p.latitude, p.longitude, :lat, :lng) <= :radius")
        List<ParlourRegModel> findNearbyParlours(@Param("lat") double latitude, @Param("lng") double longitude, @Param("radius") double radius);



}



