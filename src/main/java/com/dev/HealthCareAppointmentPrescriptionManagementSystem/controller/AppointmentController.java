package com.dev.HealthCareAppointmentPrescriptionManagementSystem.controller;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Appointment;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Doctor;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.BadRequestException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.ResourceNotFoundException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.AppointmentService;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.DoctorService;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService,
                                 DoctorService doctorService,
                                 PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment) {
        logger.info("Booking appointment: {}", appointment);

        // Validate doctor exists
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(appointment.getDoctor().getId());
        if (!doctorOpt.isPresent()) {
            logger.error("Invalid doctor ID: {}", appointment.getDoctor().getId());
            throw new BadRequestException("Invalid doctor ID: " + appointment.getDoctor().getId());
        }

        // Validate patient exists
        Optional<Patient> patientOpt = patientService.getPatientById(appointment.getPatient().getId());
        if (!patientOpt.isPresent()) {
            logger.error("Invalid patient ID: {}", appointment.getPatient().getId());
            throw new BadRequestException("Invalid patient ID: " + appointment.getPatient().getId());
        }

        Appointment savedAppointment = appointmentService.bookAppointment(appointment);
        logger.info("Appointment booked successfully with id: {}", savedAppointment.getId());
        return ResponseEntity.ok(savedAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
        logger.info("Fetching appointment with id: {}", id);
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isEmpty()) {
            logger.error("Appointment not found with id: {}", id);
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        logger.info("Appointment found: {}", appointment.get());
        return ResponseEntity.ok(appointment.get());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable String doctorId) {
        logger.info("Fetching appointments for doctor id: {}", doctorId);
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (!doctor.isPresent()) {
            logger.error("Doctor not found with id: {}", doctorId);
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor.get());
        logger.info("Found {} appointments for doctor id: {}", appointments.size(), doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable String patientId) {
        logger.info("Fetching appointments for patient id: {}", patientId);
        Optional<Patient> patient = patientService.getPatientById(patientId);
        if (!patient.isPresent()) {
            logger.error("Patient not found with id: {}", patientId);
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient.get());
        logger.info("Found {} appointments for patient id: {}", appointments.size(), patientId);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable String id) {
        logger.info("Cancelling appointment with id: {}", id);
        appointmentService.cancelAppointment(id);
        logger.info("Appointment cancelled successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments");
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        logger.info("Total appointments found: {}", allAppointments.size());
        return allAppointments;
    }
}
