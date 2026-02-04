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

    // Método para criar um novo usuário
    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    // Método para buscar um usuário por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Método para listar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Método para atualizar um usuário
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

    // Método para deletar um usuário
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // Método para verificar se um usuário está em quarentena
    public boolean isUserInQuarantine(Long id) {
        return userRepository.findById(id)
                .map(user -> ContaminationStatus.QUARANTINED.equals(user.getContaminationStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    // Método para bloquear um usuário
    public void blockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
    }

    // Método para desbloquear um usuário
    public void unblockUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setBlocked(false);
            userRepository.save(user);
        });
    }

    // Método para validar um usuário antes de salvar
    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
    }

    // Exemplo de método usando Virtual Threads para operações assíncronas
    public void processUsersConcurrently(List<Long> userIds) {
        userIds.forEach(userId -> executor.execute(() -> {
            try {
                Optional<User> user = userRepository.findById(userId);
                user.ifPresent(u -> {
                    // Processar o usuário aqui
                    System.out.println("Processando usuário: " + u.getName());
                });
            } catch (Exception e) {
                System.err.println("Erro ao processar usuário com ID: " + userId);
            }
        }));
    }
}
