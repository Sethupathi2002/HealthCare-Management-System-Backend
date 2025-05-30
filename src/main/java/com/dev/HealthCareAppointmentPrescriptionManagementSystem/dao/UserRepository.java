package com.dev.HealthCareAppointmentPrescriptionManagementSystem.dao;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    // Add to UserRepository.java
    List<User> findByRole(User.Role role);
}
