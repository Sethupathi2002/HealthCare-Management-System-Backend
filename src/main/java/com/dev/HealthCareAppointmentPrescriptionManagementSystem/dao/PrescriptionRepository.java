package com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Prescription;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    List<Prescription> findByPatient(Patient patient);
}
