package com.dev.HealthCareAppointmentPrescriptionManagementSystem.service;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao.PrescriptionRepository;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Optional<Prescription> getPrescriptionById(String id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(Patient patient) {
        return prescriptionRepository.findByPatient(patient);
    }

    @Override
    public void deletePrescription(String id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
}
