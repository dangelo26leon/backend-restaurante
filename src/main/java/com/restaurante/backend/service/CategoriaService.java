package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.dto.CategoriaCreateDTO;
import com.restaurante.backend.dto.CategoriaDTO;

public interface CategoriaService {
    List<CategoriaDTO> findAll();
    CategoriaDTO findById(Long id);
    CategoriaDTO create(CategoriaCreateDTO dto);
    CategoriaDTO update(Long id, CategoriaCreateDTO dto);
    void delete(Long id);
}
