package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Brands")
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_sponsor")
    private Boolean isSponsor;
}