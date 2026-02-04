package com.txt.backend.repository;

import com.txt.backend.model.User;
import com.txt.backend.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    List<User> findByCrew(String crew); 
    List<User> findByRole(Role role);
    List<User> findByIsActive(boolean isActive);
    
}