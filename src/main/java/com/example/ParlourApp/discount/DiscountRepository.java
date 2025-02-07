package com.example.ParlourApp.discount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountDetails,Long> {
}
