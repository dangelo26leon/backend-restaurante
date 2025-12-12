package com.restaurante.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.backend.models.enums.EstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPedido estado;
    private Long mesaId;
    private Integer mesaNumero;
    private Long meseroId;
    private String meseroNombre;
    private List<DetallePedidoDTO> detalles;
    private BigDecimal total;
}
