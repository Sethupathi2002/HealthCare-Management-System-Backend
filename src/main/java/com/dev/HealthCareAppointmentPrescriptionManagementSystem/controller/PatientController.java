package com.dev.HealthCareAppointmentPrescriptionManagementSystem.controller;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.ResourceNotFoundException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        logger.info("Creating new patient: {}", patient);
        Patient savedPatient = patientService.savePatient(patient);
        logger.info("Patient created successfully with id: {}", savedPatient.getId());
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        logger.info("Fetching patient with id: {}", id);
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with id: {}", id);
                    return new ResourceNotFoundException("Patient not found with id: " + id);
                });
        logger.info("Patient found: {}", patient);
        return ResponseEntity.ok(patient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Fetching all patients");
        List<Patient> patients = patientService.getAllPatients();
        logger.info("Total patients found: {}", patients.size());
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        logger.info("Deleting patient with id: {}", id);
        if (!patientService.getPatientById(id).isPresent()) {
            logger.error("Cannot delete - patient not found with id: {}", id);
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientService.deletePatient(id);
        logger.info("Patient deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
