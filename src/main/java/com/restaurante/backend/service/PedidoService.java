package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.dto.PedidoCreateDTO;
import com.restaurante.backend.dto.PedidoDTO;
import com.restaurante.backend.models.Usuario;
import com.restaurante.backend.models.enums.EstadoPedido;

public interface PedidoService {
    List<PedidoDTO> findAll();
    PedidoDTO findById(Long id);
    List<PedidoDTO> findByMesero(Long meseroId);
    List<PedidoDTO> findPedidosCocina();
    PedidoDTO create(PedidoCreateDTO dto, Usuario mesero);
    PedidoDTO updateEstado(Long id, EstadoPedido nuevoEstado, Usuario usuario);
}
