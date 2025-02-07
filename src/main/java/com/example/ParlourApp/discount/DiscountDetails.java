package com.example.ParlourApp.discount;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="discountTbl")
public class DiscountDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discountType")
    private String discountType;

}
