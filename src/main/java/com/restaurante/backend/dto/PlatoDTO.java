package com.restaurante.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private Long categoriaId;
    private String categoriaNombre;
    private Boolean disponible;
}
