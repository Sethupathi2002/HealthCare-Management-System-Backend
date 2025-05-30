package com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
