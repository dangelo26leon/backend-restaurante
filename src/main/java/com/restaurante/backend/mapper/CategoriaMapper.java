package com.restaurante.backend.mapper;

import org.springframework.stereotype.Component;

import com.restaurante.backend.dto.CategoriaDTO;
import com.restaurante.backend.models.Categoria;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }
}
