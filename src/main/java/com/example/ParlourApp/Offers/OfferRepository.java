package com.example.ParlourApp.Offers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<OfferRegModel, Long> {
    List<OfferRegModel> findByParlourId(Long parlourId);
}
