package com.restaurante.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.models.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByCategoriaId(Long categoriaId);
    List<Plato> findByDisponible(Boolean disponible);
    List<Plato> findByCategoriaIdAndDisponible(Long categoriaId, Boolean disponible);
    boolean existsByNombre(String nombre);
}
