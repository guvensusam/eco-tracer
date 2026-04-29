package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "barcode", unique = true, nullable = false)
    private String barcode;

    @Column(name = "name", nullable = false)
    private String name;

    // ESKİ HALİ: private Integer categoryId;
    // YENİ HALİ: Çoktan-Aza (ManyToOne) İlişki. Birçok ürün aynı kategoride olabilir.
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Aynı şekilde Marka için de gerçek objeyi bağlıyoruz.
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "eco_score")
    private Double ecoScore;

    @Column(name = "is_user_submitted")
    private Boolean isUserSubmitted;

    @Column(name = "is_approved")
    private Boolean isApproved;
}