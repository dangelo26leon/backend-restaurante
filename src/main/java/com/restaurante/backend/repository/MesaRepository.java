package com.restaurante.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.models.Mesa;
import com.restaurante.backend.models.enums.EstadoMesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(Integer numero);
    boolean existsByNumero(Integer numero);
    List<Mesa> findByEstado(EstadoMesa estado);
}
