package com.example.ParlourApp.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/discount")
public class DiscountController
{
    @Autowired
    DiscountService discountService;
    @PostMapping("/add")
    public ResponseEntity<?>addDiscount(@RequestBody DiscountDetails discountDetails)
    {
        DiscountDetails addedDiscount=discountService.addDiscount(discountDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDiscount);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiscountDetails>>getAllDiscount()
    {
        List<DiscountDetails>discount=discountService.getAllDiscount();
        return ResponseEntity.ok(discount);
    }

}
