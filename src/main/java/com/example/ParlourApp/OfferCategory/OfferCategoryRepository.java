package com.example.ParlourApp.OfferCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferCategoryRepository extends JpaRepository<OfferCategoryRegModel, Long> {
    List<OfferCategoryRegModel> findByOfferId(Long offerId);
}
