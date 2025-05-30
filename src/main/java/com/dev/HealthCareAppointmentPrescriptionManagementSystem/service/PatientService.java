package com.dev.HealthCareAppointmentPrescriptionManagementSystem.service;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient savePatient(Patient patient);
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(String id);
    void deletePatient(String id);
}
