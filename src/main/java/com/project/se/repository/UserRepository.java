package com.project.se.repository;

import com.project.se.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
    ApplicationUser findByName(String name);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);
}
