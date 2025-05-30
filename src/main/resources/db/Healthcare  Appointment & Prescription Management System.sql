-- 1. Create the schema
CREATE DATABASE IF NOT EXISTS healthcare;

-- 2. Use the schema
USE healthcare;

-- 3. Table: users
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    role ENUM('DOCTOR', 'PATIENT') NOT NULL
);

-- 4. Table: doctors
CREATE TABLE doctors (
    id VARCHAR(36) PRIMARY KEY,
    specialization VARCHAR(100),
    experience_years INT,
    qualification VARCHAR(100),
    phone VARCHAR(15),
    FOREIGN KEY (id) REFERENCES users(id)
);

ALTER TABLE doctors
DROP FOREIGN KEY doctors_ibfk_1;

ALTER TABLE doctors
ADD CONSTRAINT doctors_ibfk_1
FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE;


-- 5. Table: patients
CREATE TABLE patients (
    id VARCHAR(36) PRIMARY KEY,
    age INT,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    address VARCHAR(255),
    phone VARCHAR(15),
    FOREIGN KEY (id) REFERENCES users(id)
);

-- 6. Table: appointments
CREATE TABLE appointments (
    id VARCHAR(36) PRIMARY KEY,
    doctor_id VARCHAR(36) NOT NULL,
    patient_id VARCHAR(36) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status ENUM('BOOKED', 'CANCELLED', 'COMPLETED') DEFAULT 'BOOKED',
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- 7. Table: prescriptions
CREATE TABLE prescriptions (
    id VARCHAR(36) PRIMARY KEY,
    appointment_id VARCHAR(36) NOT NULL,
    doctor_id VARCHAR(36) NOT NULL,
    patient_id VARCHAR(36) NOT NULL,
    diagnosis TEXT,
    medicine_names TEXT,
    prescribed_date DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);
