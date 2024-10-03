package com.example.ParlourApp.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingModel,Long>
{
    List<RatingModel> findByParlour_Id(Long parlourId);
    List<RatingModel> findByRatingValue(int ratingValue);
}
