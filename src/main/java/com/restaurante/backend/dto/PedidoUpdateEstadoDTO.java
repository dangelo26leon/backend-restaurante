package com.restaurante.backend.dto;

import com.restaurante.backend.models.enums.EstadoPedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoUpdateEstadoDTO {
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoPedido estado;
}
