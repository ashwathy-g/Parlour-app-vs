package com.example.ParlourApp.gst;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gst")
public class GstData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item")
    private String item;

    @Column(name = "amount")
    private  Double amount;

}
