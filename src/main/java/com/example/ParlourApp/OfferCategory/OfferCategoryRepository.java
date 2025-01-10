package com.example.ParlourApp.OfferCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferCategoryRepository extends JpaRepository<OfferCategoryRegModel, Long> {
    List<OfferCategoryRegModel> findByOfferId(Long offerId);

    @Query("SELECT o FROM OfferCategoryRegModel o WHERE o.offerId = :parlourId")
    List<OfferCategoryRegModel> findByParlourId( Long parlourId);
}
