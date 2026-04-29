package com.example.demo.service;

import com.example.demo.model.EnvironmentalMetric;
import com.example.demo.model.Product;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EnvironmentalMetricRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class OpenFoodFactsService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final EnvironmentalMetricRepository metricRepository;
    private final ProductService productService;

    public OpenFoodFactsService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, EnvironmentalMetricRepository metricRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.metricRepository = metricRepository;
        this.productService = productService;
    }

    public Product scanAndFetchProduct(String barcode) {
        Optional<Product> existingProduct = productRepository.findByBarcode(barcode);
        if (existingProduct.isPresent()) {
            return existingProduct.get();
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("status") || !response.get("status").toString().equals("1")) {
            throw new RuntimeException("Bu ürün global sistemde de bulunamadı!");
        }

        Map<String, Object> data = (Map<String, Object>) response.get("product");
        String productName = data.containsKey("product_name") && data.get("product_name") != null
                ? data.get("product_name").toString()
                : "Bilinmeyen Ürün";

        Product newProduct = new Product();
        newProduct.setBarcode(barcode);
        newProduct.setName(productName);
        newProduct.setCategory(categoryRepository.findById(2).get());
        newProduct.setBrand(brandRepository.findById(2).get());
        newProduct.setIsUserSubmitted(true);
        newProduct.setIsApproved(false);
        newProduct = productRepository.save(newProduct);

        double ecoScore = 50.0;
        if (data.containsKey("ecoscore_score") && data.get("ecoscore_score") != null) {
            try {
                ecoScore = Double.parseDouble(data.get("ecoscore_score").toString());
            } catch (Exception e) {
            }
        }

        EnvironmentalMetric metric = new EnvironmentalMetric();
        metric.setProduct(newProduct);
        metric.setCarbonEmissionKg((100.0 - ecoScore) / 10.0);
        metric.setWaterUsageLiters(150.0);
        metric.setEnergyConsumptionKwh(1.2);
        metric.setRecyclabilityPercent(85.0);
        metricRepository.save(metric);

        return productService.calculateEcoScore(newProduct.getId());
    }
}