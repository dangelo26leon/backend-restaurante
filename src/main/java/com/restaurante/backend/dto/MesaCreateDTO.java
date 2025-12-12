package com.restaurante.backend.dto;

import com.restaurante.backend.models.enums.EstadoMesa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MesaCreateDTO {
    
    @NotNull(message = "El número de mesa es obligatorio")
    @Positive(message = "El número de mesa debe ser positivo")
    private Integer numero;
    
    private EstadoMesa estado;
}
