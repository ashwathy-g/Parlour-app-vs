package com.example.ParlourApp.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferDetails,Long> 
{

    Optional<OfferDetails> findByParlourId(Long parlourId);

    List<OfferDetails> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<OfferDetails> findByStartDate(LocalDate startDate);

    List<OfferDetails> findByEndDate(LocalDate endDate);
}
