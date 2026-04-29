INSERT INTO Categories (category_name) VALUES ('Tekstil'), ('Gıda'), ('Elektronik');

INSERT INTO Brands (brand_name, is_verified, is_sponsor) VALUES
                                                             ('EcoWear', TRUE, TRUE),
                                                             ('FastFashionX', FALSE, FALSE);

INSERT INTO Products (barcode, name, category_id, brand_id, eco_score, is_approved) VALUES
                                                                                        ('8691234567890', 'Organik Pamuk Tişört', 1, 1, 85.50, TRUE),
                                                                                        ('8690987654321', 'Standart Sentetik Tişört', 1, 2, 35.00, TRUE);

INSERT INTO Environmental_Metrics (product_id, carbon_emission_kg, water_usage_liters, energy_consumption_kwh, recyclability_percent) VALUES
                                                                                                                                          (1, 2.5, 500.00, 1.2, 95.00),
                                                                                                                                          (2, 12.0, 2500.00, 5.5, 10.00);

INSERT INTO Alternative_Products (original_product_id, recommended_product_id, is_sponsored) VALUES
    (2, 1, TRUE);

INSERT INTO Users (username, email, password_hash, total_eco_points) VALUES ('guven_susam', 'guven@example.com', 'gizlisifre123', 0);