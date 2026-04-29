package com.example.demo.repository;

import com.example.demo.model.EnvironmentalMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvironmentalMetricRepository extends JpaRepository<EnvironmentalMetric, Integer> {
    // Ürünün ID'sine göre onun çevresel verilerini bulacak özel metodumuz
    Optional<EnvironmentalMetric> findByProductId(Integer productId);
}

