package com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
