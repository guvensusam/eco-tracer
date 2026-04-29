package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ScanHistory;
import com.example.demo.model.User;
import com.example.demo.repository.ScanHistoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GamificationService {

    private final UserRepository userRepository;
    private final ScanHistoryRepository scanHistoryRepository;
    private final OpenFoodFactsService openFoodFactsService;

    public GamificationService(UserRepository userRepository, ScanHistoryRepository scanHistoryRepository, OpenFoodFactsService openFoodFactsService) {
        this.userRepository = userRepository;
        this.scanHistoryRepository = scanHistoryRepository;
        this.openFoodFactsService = openFoodFactsService;
    }

    public ScanHistory processUserScan(Integer userId, String barcode) {
        // 1. Kullanıcıyı bul, EĞER SİSTEMDE YOKSA OTOMATİK OLARAK OLUŞTUR! (Sihir burada)
        User user = userRepository.findById(userId).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername("guven_susam");
            newUser.setEmail("guven@example.com");
            newUser.setPasswordHash("gizlisifre123");
            newUser.setTotalEcoPoints(0);
            return userRepository.save(newUser);
        });

        // 2. Botumuzu çalıştır (Varsa veritabanından, yoksa internetten ürünü getir)
        Product product = openFoodFactsService.scanAndFetchProduct(barcode);

        // 3. Puanlama Algoritması
        int earnedPoints = 0;
        double score = product.getEcoScore();

        if (score >= 80.0) {
            earnedPoints = 50; // Harika seçim!
        } else if (score >= 50.0) {
            earnedPoints = 20; // Ortalama seçim
        } else {
            earnedPoints = 0; // Kötü seçim, puan yok!
        }

        // 4. Kullanıcının toplam puanını güncelle ve kaydet
        user.setTotalEcoPoints(user.getTotalEcoPoints() + earnedPoints);
        userRepository.save(user);

        // 5. Bu işlemi tarihe not düş (Scan History)
        ScanHistory history = new ScanHistory();
        history.setUser(user);
        history.setProduct(product);
        history.setEarnedPoints(earnedPoints);

        return scanHistoryRepository.save(history);
    }
}