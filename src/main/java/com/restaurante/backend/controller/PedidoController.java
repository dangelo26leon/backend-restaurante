package com.restaurante.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.backend.dto.ApiResponse;
import com.restaurante.backend.dto.PedidoCreateDTO;
import com.restaurante.backend.dto.PedidoDTO;
import com.restaurante.backend.dto.PedidoUpdateEstadoDTO;
import com.restaurante.backend.models.Usuario;
import com.restaurante.backend.models.enums.Rol;
import com.restaurante.backend.service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> findAll(@AuthenticationPrincipal Usuario usuario) {
        List<PedidoDTO> pedidos;
        
        // Si es mesero, solo ve sus propios pedidos. Admin y cocinero ven todos
        if (usuario.getRol() == Rol.MESERO) {
            pedidos = pedidoService.findByMesero(usuario.getId());
        } else {
            pedidos = pedidoService.findAll();
        }
        
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoDTO>> findById(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(pedido));
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> findMisPedidos(@AuthenticationPrincipal Usuario usuario) {
        List<PedidoDTO> pedidos = pedidoService.findByMesero(usuario.getId());
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/dashboard/cocina")
    public ResponseEntity<ApiResponse<List<PedidoDTO>>> findPedidosCocina() {
        List<PedidoDTO> pedidos = pedidoService.findPedidosCocina();
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoDTO>> create(
            @Valid @RequestBody PedidoCreateDTO dto,
            @AuthenticationPrincipal Usuario mesero) {
        PedidoDTO pedido = pedidoService.create(dto, mesero);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pedido creado exitosamente", pedido));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<PedidoDTO>> updateEstado(
            @PathVariable Long id,
            @Valid @RequestBody PedidoUpdateEstadoDTO dto,
            @AuthenticationPrincipal Usuario usuario) {
        PedidoDTO pedido = pedidoService.updateEstado(id, dto.getEstado(), usuario);
        return ResponseEntity.ok(ApiResponse.success("Estado del pedido actualizado", pedido));
    }
}
