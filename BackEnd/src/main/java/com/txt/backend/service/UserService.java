package com.txt.backend.service;

import com.txt.backend.model.User;
import com.txt.backend.repository.UserRepository;
import com.txt.backend.exception.ResourceNotFoundException;
import com.txt.backend.role.ContaminationStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to create a new user
    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    // Method to fetch a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Method to list all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to update a user
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setActive(updatedUser.isActive());
            existingUser.setBlocked(updatedUser.isBlocked());
            existingUser.setCrew(updatedUser.getCrew());
            existingUser.setContaminationStatus(updatedUser.getContaminationStatus());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    // Method to delete a user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // Method to check if a user is in quarantine
    public boolean isUserInQuarantine(Long id) {
        return userRepository.findById(id)
                .map(user -> ContaminationStatus.QUARANTINED.equals(user.getContaminationStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    // Method to block a user
    public void blockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
    }

    // Method to unblock a user
    public void unblockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(false);
            userRepository.save(user);
        });
    }

    // Method to validate a user before saving
    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
    }

    // Example method using virtual threads for asynchronous operations
    public void processUsersConcurrently(List<Long> userIds) {
        userIds.forEach(userId -> executor.execute(() -> {
            try {
                Optional<User> user = userRepository.findById(userId);
                user.ifPresent(u -> {
                    // Process user here
                    System.out.println("Processando usuário: " + u.getName());
                });
            } catch (Exception e) {
                System.err.println("Erro ao processar usuário com ID: " + userId);
            }
        }));
    }
}
