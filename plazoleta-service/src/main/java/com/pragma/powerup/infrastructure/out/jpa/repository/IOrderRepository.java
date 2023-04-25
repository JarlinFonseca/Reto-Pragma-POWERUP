package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClienteAndEstado(Long id, String estado);

    //@Query("SELECT oe FROM OrderEntity oe WHERE oe.restaurante.id = :idrestaurante AND oe.estado = :estado")
    // Page<OrderEntity> findByRestaurante_idAndEstado(@Param("idrestaurante")Long id,@Param("estado") String estado, Pageable pageable);

    Page<OrderEntity> findByRestaurante_idAndEstado(Long id, String estado, Pageable pageable);

    Boolean existsByIdAndEstado(Long id, String estado);

    //List<OrderEntity> findByRestaurante_IdAndEstado(Long id, String estado, Pageable pageable);
}
