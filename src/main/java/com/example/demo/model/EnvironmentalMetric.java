package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Environmental_Metrics")
@Data
public class EnvironmentalMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Integer id;

    // Burada ilişki kuruyoruz: Her çevresel verinin 1 tane ürünü vardır (@OneToOne)
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "carbon_emission_kg")
    private Double carbonEmissionKg;

    @Column(name = "water_usage_liters")
    private Double waterUsageLiters;

    @Column(name = "energy_consumption_kwh")
    private Double energyConsumptionKwh;

    @Column(name = "recyclability_percent")
    private Double recyclabilityPercent;
}