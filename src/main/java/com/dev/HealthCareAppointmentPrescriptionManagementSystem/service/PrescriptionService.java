package com.dev.HealthCareAppointmentPrescriptionManagementSystem.service;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Prescription;
import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    Prescription savePrescription(Prescription prescription);
    Optional<Prescription> getPrescriptionById(String id);
    List<Prescription> getPrescriptionsByPatient(Patient patient);
    void deletePrescription(String id);
    List<Prescription> getAllPrescriptions();
}
