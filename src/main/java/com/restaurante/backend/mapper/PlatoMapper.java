package com.restaurante.backend.mapper;

import org.springframework.stereotype.Component;

import com.restaurante.backend.dto.PlatoDTO;
import com.restaurante.backend.models.Plato;

@Component
public class PlatoMapper {

    public PlatoDTO toDTO(Plato plato) {
        if (plato == null) {
            return null;
        }
        return PlatoDTO.builder()
                .id(plato.getId())
                .nombre(plato.getNombre())
                .precio(plato.getPrecio())
                .descripcion(plato.getDescripcion())
                .categoriaId(plato.getCategoria() != null ? plato.getCategoria().getId() : null)
                .categoriaNombre(plato.getCategoria() != null ? plato.getCategoria().getNombre() : null)
                .disponible(plato.getDisponible())
                .build();
    }
}
