package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.dto.MesaCreateDTO;
import com.restaurante.backend.dto.MesaDTO;

public interface MesaService {
    List<MesaDTO> findAll();
    MesaDTO findById(Long id);
    List<MesaDTO> findDisponibles();
    MesaDTO create(MesaCreateDTO dto);
    MesaDTO update(Long id, MesaCreateDTO dto);
    void delete(Long id);
}
