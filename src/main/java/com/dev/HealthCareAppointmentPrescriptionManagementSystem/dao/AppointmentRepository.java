package com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Appointment;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Doctor;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
}
