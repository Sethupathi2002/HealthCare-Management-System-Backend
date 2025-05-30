package com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @Column(name = "id", length = 36, nullable = false, unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "medicine_names", columnDefinition = "TEXT")
    private String medicineNames;

    @Column(name = "prescribed_date")
    private LocalDateTime prescribedDate = LocalDateTime.now();

    public Prescription() {
        this.id = UUID.randomUUID().toString();
    }

    public Prescription(Appointment appointment, Doctor doctor, Patient patient, String diagnosis, String medicineNames) {
        this.id = UUID.randomUUID().toString();
        this.appointment = appointment;
        this.doctor = doctor;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.medicineNames = medicineNames;
    }

    public String getId() {
        return id;
    }

    // Setter is optional; typically IDs are immutable after creation
    public void setId(String id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicineNames() {
        return medicineNames;
    }

    public void setMedicineNames(String medicineNames) {
        this.medicineNames = medicineNames;
    }

    public LocalDateTime getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(LocalDateTime prescribedDate) {
        this.prescribedDate = prescribedDate;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id='" + id + '\'' +
                ", appointment=" + appointment +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", diagnosis='" + diagnosis + '\'' +
                ", medicineNames='" + medicineNames + '\'' +
                ", prescribedDate=" + prescribedDate +
                '}';
    }
}
