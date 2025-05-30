package com.dev.HealthCareAppointmentPrescriptionManagementSystem.service;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);

    void deleteUser(String id);

    List<User> getAllUsers();

    List<User> findUsersByRole(User.Role role);
}
