-- Create and use the gymmanagement database
CREATE DATABASE IF NOT EXISTS gymmanagement;
USE gymmanagement;

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id          INT             NOT NULL AUTO_INCREMENT,
    firstName   VARCHAR(100)    NOT NULL,
    lastName    VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL UNIQUE,
    phoneNumber VARCHAR(20)     NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    type        INT             NOT NULL DEFAULT 1,
                -- 0 = Admin, 1 = Member
    PRIMARY KEY (id)
);

-- Table: packages
CREATE TABLE IF NOT EXISTS packages (
    id          INT             NOT NULL AUTO_INCREMENT,
    packageName VARCHAR(150)    NOT NULL,
    duration    INT             NOT NULL,
                -- duration in months
    price       DECIMAL(10, 2)  NOT NULL,
    description TEXT,
    status      INT             NOT NULL DEFAULT 1,
                -- 1 = active, 0 = inactive
    PRIMARY KEY (id)
);

-- Table: registrations
CREATE TABLE IF NOT EXISTS registrations (
    id          INT             NOT NULL AUTO_INCREMENT,
    userId      INT             NOT NULL,
    packageId   INT             NOT NULL,
    startDate   DATE            NOT NULL,
    endDate     DATE            NOT NULL,
    total       DECIMAL(10, 2)  NOT NULL,
    status      INT             NOT NULL DEFAULT 1,
                -- 1 = active, 0 = cancelled
    PRIMARY KEY (id),
    CONSTRAINT fk_registration_user
        FOREIGN KEY (userId)    REFERENCES users (id)    ON DELETE CASCADE,
    CONSTRAINT fk_registration_package
        FOREIGN KEY (packageId) REFERENCES packages (id) ON DELETE CASCADE
);

-- Insert default admin account
-- TODO: before deploying, replace the plain-text password with a securely hashed value
--       (e.g., using bcrypt or PBKDF2) and choose a strong, non-default password.
-- Default credentials: email = admin@gym.com, password = admin123
INSERT INTO users (firstName, lastName, email, phoneNumber, password, type)
VALUES ('Admin', 'User', 'admin@gym.com', '0000000000', 'admin123', 0);
