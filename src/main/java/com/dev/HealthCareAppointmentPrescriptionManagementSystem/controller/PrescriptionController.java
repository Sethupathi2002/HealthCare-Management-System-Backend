package com.dev.HealthCareAppointmentPrescriptionManagementSystem.controller;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Prescription;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.ResourceNotFoundException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.PatientService;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    private final PrescriptionService prescriptionService;
    private final PatientService patientService;

    public PrescriptionController(PrescriptionService prescriptionService,
                                  PatientService patientService) {
        this.prescriptionService = prescriptionService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        logger.info("Creating new prescription: {}", prescription);
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        logger.info("Prescription created with id: {}", savedPrescription.getId());
        return ResponseEntity.ok(savedPrescription);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
        logger.info("Fetching prescription with id: {}", id);
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if (prescription.isEmpty()) {
            logger.error("Prescription not found with id: {}", id);
            throw new ResourceNotFoundException("Prescription not found with id: " + id);
        }
        logger.info("Prescription found: {}", prescription.get());
        return ResponseEntity.ok(prescription.get());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable String patientId) {
        logger.info("Fetching prescriptions for patient id: {}", patientId);
        Optional<Patient> patient = patientService.getPatientById(patientId);
        if (patient.isEmpty()) {
            logger.error("Patient not found with id: {}", patientId);
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patient.get());
        logger.info("Found {} prescriptions for patient id: {}", prescriptions.size(), patientId);
        return ResponseEntity.ok(prescriptions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        logger.info("Deleting prescription with id: {}", id);
        prescriptionService.deletePrescription(id);
        logger.info("Prescription deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        logger.info("Fetching all prescriptions");
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        logger.info("Total prescriptions found: {}", prescriptions.size());
        return prescriptions;
    }
}
