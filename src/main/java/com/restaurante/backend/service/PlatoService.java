package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.dto.PlatoCreateDTO;
import com.restaurante.backend.dto.PlatoDTO;
import com.restaurante.backend.dto.PlatoUpdateDTO;

public interface PlatoService {
    List<PlatoDTO> findAll();
    PlatoDTO findById(Long id);
    List<PlatoDTO> findByCategoria(Long categoriaId);
    List<PlatoDTO> findDisponibles();
    PlatoDTO create(PlatoCreateDTO dto);
    PlatoDTO update(Long id, PlatoUpdateDTO dto);
    void delete(Long id);
}
