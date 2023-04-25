package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<RolEntity, Long> {
}
