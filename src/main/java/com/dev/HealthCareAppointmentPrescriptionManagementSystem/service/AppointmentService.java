package com.dev.HealthCareAppointmentPrescriptionManagementSystem.service;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Appointment;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Doctor;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment bookAppointment(Appointment appointment);
    Optional<Appointment> getAppointmentById(String id);
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
    List<Appointment> getAppointmentsByPatient(Patient patient);
    void cancelAppointment(String id);
    List<Appointment> getAllAppointments();
}
