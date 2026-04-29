CREATE TABLE IF NOT EXISTS Categories (
                                          category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Brands (
                                      brand_id INT IDENTITY(1,1) PRIMARY KEY,
    brand_name VARCHAR(100) NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    is_sponsor BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS Users (
                                     user_id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    total_eco_points INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS Products (
                                        product_id INT IDENTITY(1,1) PRIMARY KEY,
    barcode VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    eco_score DECIMAL(5,2) DEFAULT NULL,
    is_user_submitted BOOLEAN DEFAULT FALSE,
    is_approved BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES Categories(category_id),
    FOREIGN KEY (brand_id) REFERENCES Brands(brand_id)
    );

CREATE TABLE IF NOT EXISTS Environmental_Metrics (
                                                     metric_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT UNIQUE NOT NULL,
    carbon_emission_kg DECIMAL(10,2),
    water_usage_liters DECIMAL(10,2),
    energy_consumption_kwh DECIMAL(10,2),
    recyclability_percent DECIMAL(5,2),
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Scan_History (
                                            scan_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    earned_points INT DEFAULT 0,
    scan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Alternative_Products (
                                                    alt_id INT IDENTITY(1,1) PRIMARY KEY,
    original_product_id INT NOT NULL,
    recommended_product_id INT NOT NULL,
    is_sponsored BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (original_product_id) REFERENCES Products(product_id),
    FOREIGN KEY (recommended_product_id) REFERENCES Products(product_id)
    );