package com.example.ParlourApp.offer;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "offerTbl")
public class OfferDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "typeId")
    private Long typeId;

    @Column(name = "offerName")
    private String offerName;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "parlourId")
    private Long parlourId;
}
