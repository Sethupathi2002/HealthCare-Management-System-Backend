package com.dev.HealthCareAppointmentPrescriptionManagementSystem.controller;

import com.dev.HealthCareAppointmentPrescriptionManagementSystem.entity.User;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.exception.ResourceNotFoundException;
import com.dev.HealthCareAppointmentPrescriptionManagementSystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        logger.info("Fetching user with id: {}", id);
        return userService.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user with username: {}", username);
        return userService.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found with username: " + username);
                });
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        User savedUser = userService.saveUser(user);
        logger.info("User created successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        try {
            User existingUser = userService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setRole(userDetails.getRole());

            User updatedUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        logger.info("Deleting user with id: {}", id);
        User existingUser = userService.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        userService.deleteUser(id);
        logger.info("User deleted successfully with id: {}", id);
    }
}
