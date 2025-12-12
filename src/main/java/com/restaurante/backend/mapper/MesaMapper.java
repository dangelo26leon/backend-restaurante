package com.restaurante.backend.mapper;

import org.springframework.stereotype.Component;

import com.restaurante.backend.dto.MesaDTO;
import com.restaurante.backend.models.Mesa;

@Component
public class MesaMapper {

    public MesaDTO toDTO(Mesa mesa) {
        if (mesa == null) {
            return null;
        }
        return MesaDTO.builder()
                .id(mesa.getId())
                .numero(mesa.getNumero())
                .estado(mesa.getEstado())
                .build();
    }
}
