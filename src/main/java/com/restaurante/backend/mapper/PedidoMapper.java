package com.restaurante.backend.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.restaurante.backend.dto.DetallePedidoDTO;
import com.restaurante.backend.dto.PedidoDTO;
import com.restaurante.backend.models.DetallePedido;
import com.restaurante.backend.models.Pedido;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado())
                .mesaId(pedido.getMesa() != null ? pedido.getMesa().getId() : null)
                .mesaNumero(pedido.getMesa() != null ? pedido.getMesa().getNumero() : null)
                .meseroId(pedido.getMesero() != null ? pedido.getMesero().getId() : null)
                .meseroNombre(pedido.getMesero() != null ? 
                        pedido.getMesero().getNombre() + " " + pedido.getMesero().getApellido() : null)
                .detalles(pedido.getDetalles() != null ? 
                        pedido.getDetalles().stream()
                                .map(this::toDetalleDTO)
                                .collect(Collectors.toList()) : null)
                .total(pedido.getTotal())
                .build();
    }

    public DetallePedidoDTO toDetalleDTO(DetallePedido detalle) {
        if (detalle == null) {
            return null;
        }
        return DetallePedidoDTO.builder()
                .id(detalle.getId())
                .platoId(detalle.getPlato() != null ? detalle.getPlato().getId() : null)
                .platoNombre(detalle.getPlato() != null ? detalle.getPlato().getNombre() : null)
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
