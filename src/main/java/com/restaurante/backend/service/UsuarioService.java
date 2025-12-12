package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.dto.UsuarioCreateDTO;
import com.restaurante.backend.dto.UsuarioDTO;
import com.restaurante.backend.dto.UsuarioUpdateDTO;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(Long id);
    UsuarioDTO create(UsuarioCreateDTO dto);
    UsuarioDTO update(Long id, UsuarioUpdateDTO dto);
    void delete(Long id);
}
