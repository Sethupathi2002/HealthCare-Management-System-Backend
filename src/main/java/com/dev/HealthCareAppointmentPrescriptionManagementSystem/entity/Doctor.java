package com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "experience_years")
    private int experienceYears;

    @Column(name = "qualification", length = 100)
    private String qualification;

    @Column(name = "phone", length = 15)
    private String phone;

    public Doctor() {}

    public Doctor(User user, String specialization, int experienceYears, String qualification, String phone) {
        this.user = user;
        this.id = user.getId();
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.qualification = qualification;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.id = user != null ? user.getId() : null;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experienceYears=" + experienceYears +
                ", qualification='" + qualification + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
