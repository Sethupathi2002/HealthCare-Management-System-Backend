package com.dev.HealthCareAppointmentPrescriptionManagementSystem.controller;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Doctor;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.User;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.ResourceNotFoundException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.DoctorService;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.UserService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorService doctorService;
    private final UserService userService;

    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors");
        List<Doctor> doctors = doctorService.getAllDoctors();
        // Eagerly fetch user details
        doctors.forEach(doctor -> {
            if (doctor.getUser() != null) {
                Hibernate.initialize(doctor.getUser());
            }
        });
        return doctors;
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable String id) {
        logger.info("Fetching doctor with id: {}", id);
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor not found with id: " + id);
                });
        // Eagerly fetch user details
        if (doctor.getUser() != null) {
            Hibernate.initialize(doctor.getUser());
        }
        return doctor;
    }

    // To get only the role of Doctors
    @GetMapping("/doctor-users")
    public List<User> getDoctorUsers() {
        logger.info("Fetching all users with doctor role");
        return userService.findUsersByRole(User.Role.DOCTOR);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        if (doctor.getUser() == null || doctor.getUser().getId() == null) {
            logger.error("Cannot create doctor without a valid user reference");
            throw new ResourceNotFoundException("User must exist and have an ID to create a Doctor");
        }

        logger.info("Creating new doctor for user id: {}", doctor.getUser().getId());

        User user = userService.findById(doctor.getUser().getId())
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", doctor.getUser().getId());
                    return new ResourceNotFoundException("User not found with id: " + doctor.getUser().getId());
                });

        doctor.setUser(user);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        logger.info("Doctor created successfully with id: {}", savedDoctor.getId());
        return savedDoctor;
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable String id, @RequestBody Doctor doctorDetails) {
        logger.info("Updating doctor with id: {}", id);
        Doctor existingDoctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor not found with id: " + id);
                });

        existingDoctor.setSpecialization(doctorDetails.getSpecialization());
        existingDoctor.setExperienceYears(doctorDetails.getExperienceYears());
        existingDoctor.setQualification(doctorDetails.getQualification());
        existingDoctor.setPhone(doctorDetails.getPhone());

        Doctor updatedDoctor = doctorService.saveDoctor(existingDoctor);
        logger.info("Doctor updated successfully with id: {}", id);
        return updatedDoctor;
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable String id) {
        logger.info("Deleting doctor with id: {}", id);
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> {
                    logger.error("Doctor not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor not found with id: " + id);
                });

        doctorService.deleteDoctor(id);
        logger.info("Doctor deleted successfully with id: {}", id);
    }
}
