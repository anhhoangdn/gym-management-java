CREATE DATABASE [java-gym];
GO

USE [java-gym];
GO

CREATE TABLE users (
    id          INT             NOT NULL IDENTITY(1,1),
    firstName   NVARCHAR(100)   NOT NULL,
    lastName    NVARCHAR(100)   NOT NULL,
    email       NVARCHAR(150)   NOT NULL,
    phoneNumber NVARCHAR(20)    NOT NULL,
    password    NVARCHAR(320)   NOT NULL,
    type        INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
);
GO

CREATE TABLE packages (
    id          INT             NOT NULL IDENTITY(1,1),
    packageName NVARCHAR(150)   NOT NULL,
    duration    INT             NOT NULL,
    price       DECIMAL(10, 2)  NOT NULL,
    description NVARCHAR(MAX),
    status      INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);
GO

CREATE TABLE registrations (
    id          INT             NOT NULL IDENTITY(1,1),
    userId      INT             NOT NULL,
    packageId   INT             NOT NULL,
    startDate   DATE            NOT NULL,
    endDate     DATE            NOT NULL,
    total       DECIMAL(10, 2)  NOT NULL,
    status      INT             NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_reg_user
        FOREIGN KEY (userId)    REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_reg_package
        FOREIGN KEY (packageId) REFERENCES packages (id) ON DELETE CASCADE
);
GO

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
VALUES ('Admin', 'User', 'admin@gym.com', '0900000000', 'sha256$240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 0);
GO

INSERT INTO packages (packageName, duration, price, description, status)
VALUES (N'Gói tháng', 1, 360000.00, N'Gói tập 1 tháng', 1),
       (N'Gói 6 tháng', 6, 1960000.00, N'Gói tập 6 tháng', 1),
       (N'Gói 1 năm', 12, 3600000.00, N'Gói tập 1 năm', 1);
GO

INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
VALUES 
(N'Nguyễn Văn', N'An', 'nguyenvanan@gmail.com', '0912345601', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Trần Thị Thanh', N'Xuân', 'tranthithanhxuan@gmail.com', '0912345602', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Lê Quang', N'Huy', 'lequanghuy@gmail.com', '0912345603', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Phạm Minh', N'Khoa', 'phamminhkhoa@gmail.com', '0912345604', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Đoàn Thu', N'Hà', 'doanthuha@gmail.com', '0912345605', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Bùi Đức', N'Long', 'buiduclong@gmail.com', '0912345606', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Võ Ngọc', N'Anh', 'vongocanh@gmail.com', '0912345607', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Đặng Gia', N'Bảo', 'dangiabao@gmail.com', '0912345608', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Hoàng Thị', N'Mỹ Linh', 'hoangthimylinh@gmail.com', '0912345609', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1),
(N'Nguyễn Tuấn', N'Kiệt', 'nguyentuankiet@gmail.com', '0912345610', 'sha256$36113b2ce24e54ba05763b03657fc68d3eece191a8df9e81b672a91dd93f2f0a', 1);
GO

INSERT INTO registrations (userId, packageId, startDate, endDate, total, status)
VALUES 
((SELECT id FROM users WHERE email='nguyenvanan@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói tháng'), '2026-01-05', '2026-02-05', 360000.00, 1),
((SELECT id FROM users WHERE email='tranthithanhxuan@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói tháng'), '2026-02-10', '2026-03-10', 360000.00, 1),
((SELECT id FROM users WHERE email='lequanghuy@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói tháng'), '2026-03-15', '2026-04-15', 360000.00, 1),
((SELECT id FROM users WHERE email='phamminhkhoa@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói tháng'), '2026-04-20', '2026-05-20', 360000.00, 1),
((SELECT id FROM users WHERE email='doanthuha@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 6 tháng'), '2025-11-01', '2026-05-01', 1960000.00, 1),
((SELECT id FROM users WHERE email='buiduclong@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 6 tháng'), '2025-12-10', '2026-06-10', 1960000.00, 1),
((SELECT id FROM users WHERE email='vongocanh@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 6 tháng'), '2026-01-20', '2026-07-20', 1960000.00, 1),
((SELECT id FROM users WHERE email='dangiabao@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 1 năm'), '2025-05-01', '2026-05-01', 3600000.00, 1),
((SELECT id FROM users WHERE email='hoangthimylinh@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 1 năm'), '2025-07-15', '2026-07-15', 3600000.00, 1),
((SELECT id FROM users WHERE email='nguyentuankiet@gmail.com'), (SELECT id FROM packages WHERE packageName=N'Gói 1 năm'), '2025-10-01', '2026-10-01', 3600000.00, 1);
GO
