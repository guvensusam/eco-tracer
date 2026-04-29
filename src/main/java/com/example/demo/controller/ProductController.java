package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ScanHistory;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.GamificationService;
import com.example.demo.service.OpenFoodFactsService;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OpenFoodFactsService openFoodFactsService;
    private final GamificationService gamificationService;

    // Tüm servisleri birbirine bağlayan tek ve temiz kurucu metod
    public ProductController(ProductRepository productRepository,
                             ProductService productService,
                             OpenFoodFactsService openFoodFactsService,
                             GamificationService gamificationService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.openFoodFactsService = openFoodFactsService;
        this.gamificationService = gamificationService;
    }

    @GetMapping("/")
    public String home() {
        return "Eco-Trace Sürdürülebilirlik Motoru Aktif!";
    }

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/api/products/{id}/eco-score")
    public Product getProductEcoScore(@PathVariable Integer id) {
        return productService.calculateEcoScore(id);
    }

    @GetMapping("/api/products/{id}/alternatives")
    public List<Product> getAlternatives(@PathVariable Integer id) {
        return productService.suggestBetterAlternatives(id);
    }

    @GetMapping("/api/products/scan/{barcode}")
    public Product scanProduct(@PathVariable String barcode) {
        return openFoodFactsService.scanAndFetchProduct(barcode);
    }

    // OYUNLAŞTIRMA SİSTEMİ BARKOD OKUYUCU
    @GetMapping("/api/scan/{userId}/{barcode}")
    public ScanHistory userScansProduct(@PathVariable Integer userId, @PathVariable String barcode) {
        return gamificationService.processUserScan(userId, barcode);
    }
}
