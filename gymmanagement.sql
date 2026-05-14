CREATE DATABASE IF NOT EXISTS `java-gym`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `java-gym`;

CREATE TABLE IF NOT EXISTS users (
    id          INT             NOT NULL AUTO_INCREMENT,
    firstName   VARCHAR(100)    NOT NULL,
    lastName    VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL,
    phoneNumber VARCHAR(20)     NOT NULL,
    password    VARCHAR(320)    NOT NULL,
    type        INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uq_users_email (email)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS packages (
    id          INT             NOT NULL AUTO_INCREMENT,
    packageName VARCHAR(150)    NOT NULL,
    duration    INT             NOT NULL,
    price       DECIMAL(10, 2)  NOT NULL,
    description TEXT,
    status      INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS registrations (
    id          INT             NOT NULL AUTO_INCREMENT,
    userId      INT             NOT NULL,
    packageId   INT             NOT NULL,
    startDate   DATE            NOT NULL,
    endDate     DATE            NOT NULL,
    total       DECIMAL(10, 2)  NOT NULL,
    status      INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_reg_user
        FOREIGN KEY (userId)    REFERENCES users    (id) ON DELETE CASCADE,
    CONSTRAINT fk_reg_package
        FOREIGN KEY (packageId) REFERENCES packages (id) ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Admin', 'User', 'admin@gym.com', '0900000000',
       CONCAT('sha256$', SHA2('admin123', 256)), 0
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@gym.com');


INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Gói tháng', 1, 360000.00, 'Gói tập 1 tháng', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Gói tháng');

INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Gói 6 tháng', 6, 1960000.00, 'Gói tập 6 tháng', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Gói 6 tháng');

INSERT INTO packages (packageName, duration, price, description, status)
SELECT 'Gói 1 năm', 12, 3600000.00, 'Gói tập 1 năm', 1
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE packageName = 'Gói 1 năm');


INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Nguyễn Văn', 'An', 'nguyenvanan@gmail.com', '0912345601',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'nguyenvanan@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Trần Thị Thanh', 'Xuân', 'tranthithanhxuan@gmail.com', '0912345602',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'tranthithanhxuan@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Lê Quang', 'Huy', 'lequanghuy@gmail.com', '0912345603',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'lequanghuy@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Phạm Minh', 'Khoa', 'phamminhkhoa@gmail.com', '0912345604',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'phamminhkhoa@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Đoàn Thu', 'Hà', 'doanthuha@gmail.com', '0912345605',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'doanthuha@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Bùi Đức', 'Long', 'buiduclong@gmail.com', '0912345606',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'buiduclong@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Võ Ngọc', 'Anh', 'vongocanh@gmail.com', '0912345607',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'vongocanh@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Đặng Gia', 'Bảo', 'dangiabao@gmail.com', '0912345608',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'dangiabao@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Hoàng Thị', 'Mỹ Linh', 'hoangthimylinh@gmail.com', '0912345609',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hoangthimylinh@gmail.com');

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
SELECT 'Nguyễn Tuấn', 'Kiệt', 'nguyentuankiet@gmail.com', '0912345610',
       CONCAT('sha256$', SHA2('member123', 256)), 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'nguyentuankiet@gmail.com');


INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-01-05', DATE_ADD('2026-01-05', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói tháng'
WHERE u.email = 'nguyenvanan@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-02-10', DATE_ADD('2026-02-10', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói tháng'
WHERE u.email = 'tranthithanhxuan@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-03-15', DATE_ADD('2026-03-15', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói tháng'
WHERE u.email = 'lequanghuy@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-04-20', DATE_ADD('2026-04-20', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói tháng'
WHERE u.email = 'phamminhkhoa@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2025-11-01', DATE_ADD('2025-11-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 6 tháng'
WHERE u.email = 'doanthuha@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2025-12-10', DATE_ADD('2025-12-10', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 6 tháng'
WHERE u.email = 'buiduclong@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2026-01-20', DATE_ADD('2026-01-20', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 6 tháng'
WHERE u.email = 'vongocanh@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2025-05-01', DATE_ADD('2025-05-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 1 năm'
WHERE u.email = 'dangiabao@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2025-07-15', DATE_ADD('2025-07-15', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 1 năm'
WHERE u.email = 'hoangthimylinh@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
SELECT u.id, p.id, '2025-10-01', DATE_ADD('2025-10-01', INTERVAL p.duration MONTH), p.price, 1
FROM users u JOIN packages p ON p.packageName = 'Gói 1 năm'
WHERE u.email = 'nguyentuankiet@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.userId = u.id AND r.packageId = p.id);
