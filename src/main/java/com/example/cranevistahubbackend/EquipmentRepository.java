package com.example.cranevistahubbackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // JpaRepository provides all basic CRUD operations.
    // We can add custom query methods here if needed in the future.
}
