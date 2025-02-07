package com.example.ParlourApp.discount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;
    public DiscountDetails addDiscount(DiscountDetails discountDetails)
    {
        return discountRepository.save(discountDetails);
    }

    public List<DiscountDetails> getAllDiscount()
    {
        return discountRepository.findAll();
    }
}
