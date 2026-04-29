package com.example.demo.service;

import com.example.demo.model.EnvironmentalMetric;
import com.example.demo.model.Product;
import com.example.demo.repository.EnvironmentalMetricRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EnvironmentalMetricRepository metricRepository;

    public ProductService(ProductRepository productRepository, EnvironmentalMetricRepository metricRepository) {
        this.productRepository = productRepository;
        this.metricRepository = metricRepository;
    }

    /**
     * 1. ECO-SCORE HESAPLAMA ALGORİTMASI
     * Çevresel metrikleri alıp matematiksel bir skor üretir.
     */
    public Product calculateEcoScore(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        EnvironmentalMetric metric = metricRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Bu ürüne ait çevresel veri bulunamadı!"));

        // Temel puan 100 üzerinden başlar
        double baseScore = 100.0;

        // Algoritma Parametreleri (Raporundaki mantığa göre)
        double carbonPenalty = metric.getCarbonEmissionKg() * 2.5;    // Karbon ayak izi etkisi
        double waterPenalty = metric.getWaterUsageLiters() / 100.0;    // Su tüketimi etkisi
        double energyPenalty = metric.getEnergyConsumptionKwh() * 1.5; // Enerji tüketimi etkisi
        double recyclingBonus = metric.getRecyclabilityPercent() * 0.2; // Geri dönüşüm bonusu

        double finalScore = baseScore - carbonPenalty - waterPenalty - energyPenalty + recyclingBonus;

        // Skoru 0 ile 100 arasında sınırla
        finalScore = Math.max(0.0, Math.min(100.0, finalScore));

        // 2 ondalık basamağa yuvarla ve ürünü güncelle
        product.setEcoScore(Math.round(finalScore * 100.0) / 100.0);
        return productRepository.save(product);
    }

    /**
     * 2. ALTERNATİF ÜRÜN ÖNERİ MOTORU
     * Aynı kategorideki daha çevreci ürünleri bulur.
     */
    public List<Product> suggestBetterAlternatives(Integer productId) {
        Product currentProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        // Eğer ürünün skoru henüz yoksa hesapla
        if (currentProduct.getEcoScore() == null) {
            currentProduct = calculateEcoScore(productId);
        }

        // Kendi kategorisindeki daha yüksek puanlı ürünleri getir
        return productRepository.findByCategoryIdAndEcoScoreGreaterThanOrderByEcoScoreDesc(
                currentProduct.getCategory().getId(),
                currentProduct.getEcoScore()
        );
    }
}