package com.example.ParlourApp.appcharge;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "appCharge")
public class ChargeDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "startRange")
    private Double startRange;

    @Column(name = "endRange")
    private Double endRange;

    @Column(name = "percentage")
    private Double percentage;
}
