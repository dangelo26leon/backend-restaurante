package com.restaurante.backend.dto;

import com.restaurante.backend.models.enums.EstadoMesa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MesaDTO {
    private Long id;
    private Integer numero;
    private EstadoMesa estado;
}
