package com.example.cranevistahubbackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // JpaRepository provides all basic CRUD operations.
    // We can add custom query methods here if needed in the future.
    /**
     * 根据设备编码查找设备。
     * Spring Data JPA 会根据方法名自动生成查询。
     * @param code 设备编码
     * @return 一个包含设备（如果找到）或为空的 Optional
     */
    Optional<Equipment> findByCode(String code);
}
