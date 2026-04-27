-- =====================================================
-- Gym Management - Schema + Seed Data
-- Database name: java-gym
-- =====================================================

CREATE DATABASE IF NOT EXISTS `java-gym`;
USE `java-gym`;

-- -----------------
-- TABLE: users
-- -----------------
CREATE TABLE IF NOT EXISTS users (
    id          INT             NOT NULL AUTO_INCREMENT,
    firstName   VARCHAR(100)    NOT NULL,
    lastName    VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL UNIQUE,
    phoneNumber VARCHAR(20)     NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    type        INT             NOT NULL DEFAULT 1, -- 0=Admin, 1=Member
    PRIMARY KEY (id)
);

-- -----------------
-- TABLE: packages
-- -----------------
CREATE TABLE IF NOT EXISTS packages (
    id          INT             NOT NULL AUTO_INCREMENT,
    packageName VARCHAR(150)    NOT NULL,
    duration    INT             NOT NULL, -- months
    price       DECIMAL(10, 2)  NOT NULL,
    description TEXT,
    status      INT             NOT NULL DEFAULT 1, -- 1=active, 0=inactive
    PRIMARY KEY (id)
);

-- -----------------
-- TABLE: registrations
-- -----------------
CREATE TABLE IF NOT EXISTS registrations (
    id          INT             NOT NULL AUTO_INCREMENT,
    userId      INT             NOT NULL,
    packageId   INT             NOT NULL,
    startDate   DATE            NOT NULL,
    endDate     DATE            NOT NULL,
    total       DECIMAL(10, 2)  NOT NULL,
    status      INT             NOT NULL DEFAULT 1, -- 1=active, 0=cancelled
    PRIMARY KEY (id),
    CONSTRAINT fk_registration_user
        FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_registration_package
        FOREIGN KEY (packageId) REFERENCES packages (id) ON DELETE CASCADE
);

-- -----------------
-- DEFAULT ADMIN (hashed password)
-- raw password: admin123
-- -----------------
INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Admin', 'User', 'admin@gym.com', '0900000000', CONCAT('sha256$', SHA2('admin123', 256)), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@gym.com');

-- -----------------
-- PACKAGE TIERS
-- -----------------
INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Goi thang', 1, 360000, 'Goi tap 1 thang', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Goi thang');

INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Goi 6 thang', 6, 1960000, 'Goi tap 6 thang', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Goi 6 thang');

INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Goi 1 nam', 12, 3600000, 'Goi tap 1 nam', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Goi 1 nam');

-- -----------------
-- 10 MEMBER USERS (hashed password: member123)
-- -----------------
INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Nguyen Van', 'An', 'nguyenvanan@gmail.com', '0912345601', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'nguyenvanan@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Tran Thi Thanh', 'Xuan', 'tranthithanhxuan@gmail.com', '0912345602', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'tranthithanhxuan@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Le Quang', 'Huy', 'lequanghuy@gmail.com', '0912345603', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'lequanghuy@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Pham Minh', 'Khoa', 'phamminhkhoa@gmail.com', '0912345604', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'phamminhkhoa@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Doan Thu', 'Ha', 'doanthuha@gmail.com', '0912345605', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'doanthuha@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Bui Duc', 'Long', 'buiduclong@gmail.com', '0912345606', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'buiduclong@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Vo Ngoc', 'Anh', 'vongocanh@gmail.com', '0912345607', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'vongocanh@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Dang Gia', 'Bao', 'dangiabao@gmail.com', '0912345608', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'dangiabao@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Hoang Thi', 'My Linh', 'hoangthimylinh@gmail.com', '0912345609', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hoangthimylinh@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Nguyen Tuan', 'Kiet', 'nguyentuankiet@gmail.com', '0912345610', CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'nguyentuankiet@gmail.com');

-- -----------------
-- SAMPLE REGISTRATIONS
-- -----------------
INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-04-01', DATE_ADD('2026-04-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u
JOIN packages p ON p.packageName = 'Goi thang'
WHERE u.email IN ('nguyenvanan@gmail.com','tranthithanhxuan@gmail.com','lequanghuy@gmail.com','phamminhkhoa@gmail.com')
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-04-01', DATE_ADD('2026-04-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u
JOIN packages p ON p.packageName = 'Goi 6 thang'
WHERE u.email IN ('doanthuha@gmail.com','buiduclong@gmail.com','vongocanh@gmail.com')
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-04-01', DATE_ADD('2026-04-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u
JOIN packages p ON p.packageName = 'Goi 1 nam'
WHERE u.email IN ('dangiabao@gmail.com','hoangthimylinh@gmail.com','nguyentuankiet@gmail.com')
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);
