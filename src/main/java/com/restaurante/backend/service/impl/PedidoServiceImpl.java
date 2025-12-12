package com.restaurante.backend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.backend.dto.DetallePedidoCreateDTO;
import com.restaurante.backend.dto.PedidoCreateDTO;
import com.restaurante.backend.dto.PedidoDTO;
import com.restaurante.backend.exception.BadRequestException;
import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.exception.UnauthorizedOperationException;
import com.restaurante.backend.mapper.PedidoMapper;
import com.restaurante.backend.models.DetallePedido;
import com.restaurante.backend.models.Mesa;
import com.restaurante.backend.models.Pedido;
import com.restaurante.backend.models.Plato;
import com.restaurante.backend.models.Usuario;
import com.restaurante.backend.models.enums.EstadoMesa;
import com.restaurante.backend.models.enums.EstadoPedido;
import com.restaurante.backend.models.enums.Rol;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.repository.PedidoRepository;
import com.restaurante.backend.repository.PlatoRepository;
import com.restaurante.backend.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByMesero(Long meseroId) {
        return pedidoRepository.findByMeseroId(meseroId).stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findPedidosCocina() {
        return pedidoRepository.findPedidosPendientesYEnProceso().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO create(PedidoCreateDTO dto, Usuario mesero) {
        // Validar que el usuario sea mesero o admin
        if (mesero.getRol() != Rol.MESERO && mesero.getRol() != Rol.ADMIN) {
            throw new UnauthorizedOperationException("Solo los meseros o administradores pueden crear pedidos");
        }

        // Buscar y validar la mesa
        Mesa mesa = mesaRepository.findById(dto.getMesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa", "id", dto.getMesaId()));

        if (mesa.getEstado() == EstadoMesa.OCUPADA) {
            throw new BadRequestException("La mesa " + mesa.getNumero() + " ya está ocupada");
        }

        // Crear el pedido
        Pedido pedido = Pedido.builder()
                .fecha(LocalDateTime.now())
                .estado(EstadoPedido.PENDIENTE)
                .mesa(mesa)
                .mesero(mesero)
                .detalles(new ArrayList<>())
                .build();

        // Procesar los detalles del pedido
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedidoCreateDTO detalleDTO : dto.getDetalles()) {
            Plato plato = platoRepository.findById(detalleDTO.getPlatoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Plato", "id", detalleDTO.getPlatoId()));

            if (!plato.getDisponible()) {
                throw new BadRequestException("El plato '" + plato.getNombre() + "' no está disponible");
            }

            BigDecimal subtotal = plato.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));

            DetallePedido detalle = DetallePedido.builder()
                    .plato(plato)
                    .cantidad(detalleDTO.getCantidad())
                    .precioUnitario(plato.getPrecio())
                    .subtotal(subtotal)
                    .pedido(pedido)
                    .build();

            pedido.getDetalles().add(detalle);
            total = total.add(subtotal);
        }

        pedido.setTotal(total);

        // Actualizar estado de la mesa a OCUPADA
        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        // Guardar el pedido
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoDTO updateEstado(Long id, EstadoPedido nuevoEstado, Usuario usuario) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));

        // Validar permisos según rol
        Rol rol = usuario.getRol();
        EstadoPedido estadoActual = pedido.getEstado();

        // Validar transiciones de estado válidas
        validarTransicionEstado(estadoActual, nuevoEstado);

        // Validar permisos según el nuevo estado
        if (nuevoEstado == EstadoPedido.EN_PROCESO || nuevoEstado == EstadoPedido.TERMINADO) {
            if (rol != Rol.COCINERO && rol != Rol.ADMIN) {
                throw new UnauthorizedOperationException("Solo los cocineros o administradores pueden cambiar a este estado");
            }
        }

        if (nuevoEstado == EstadoPedido.ENTREGADO) {
            if (rol != Rol.MESERO && rol != Rol.ADMIN) {
                throw new UnauthorizedOperationException("Solo los meseros o administradores pueden marcar como entregado");
            }
            // Verificar que el mesero sea el dueño del pedido (si no es admin)
            if (rol == Rol.MESERO && !pedido.getMesero().getId().equals(usuario.getId())) {
                throw new UnauthorizedOperationException("Solo puedes entregar tus propios pedidos");
            }
        }

        // Actualizar el estado
        pedido.setEstado(nuevoEstado);

        // Si el pedido se marca como ENTREGADO, liberar la mesa
        if (nuevoEstado == EstadoPedido.ENTREGADO) {
            Mesa mesa = pedido.getMesa();
            mesa.setEstado(EstadoMesa.DISPONIBLE);
            mesaRepository.save(mesa);
        }

        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedido);
    }

    private void validarTransicionEstado(EstadoPedido actual, EstadoPedido nuevo) {
        switch (actual) {
            case PENDIENTE:
                if (nuevo != EstadoPedido.EN_PROCESO && nuevo != EstadoPedido.TERMINADO) {
                    throw new BadRequestException("Un pedido pendiente solo puede pasar a 'En proceso' o 'Terminado'");
                }
                break;
            case EN_PROCESO:
                if (nuevo != EstadoPedido.TERMINADO) {
                    throw new BadRequestException("Un pedido en proceso solo puede pasar a 'Terminado'");
                }
                break;
            case TERMINADO:
                if (nuevo != EstadoPedido.ENTREGADO) {
                    throw new BadRequestException("Un pedido terminado solo puede pasar a 'Entregado'");
                }
                break;
            case ENTREGADO:
                throw new BadRequestException("Un pedido entregado no puede cambiar de estado");
            default:
                throw new BadRequestException("Estado de pedido no válido");
        }
    }
}
