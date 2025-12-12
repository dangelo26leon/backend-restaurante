package com.restaurante.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.backend.dto.ApiResponse;
import com.restaurante.backend.dto.PlatoCreateDTO;
import com.restaurante.backend.dto.PlatoDTO;
import com.restaurante.backend.dto.PlatoUpdateDTO;
import com.restaurante.backend.service.PlatoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlatoDTO>>> findAll() {
        List<PlatoDTO> platos = platoService.findAll();
        return ResponseEntity.ok(ApiResponse.success(platos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlatoDTO>> findById(@PathVariable Long id) {
        PlatoDTO plato = platoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(plato));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<ApiResponse<List<PlatoDTO>>> findByCategoria(@PathVariable Long categoriaId) {
        List<PlatoDTO> platos = platoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(ApiResponse.success(platos));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<PlatoDTO>>> findDisponibles() {
        List<PlatoDTO> platos = platoService.findDisponibles();
        return ResponseEntity.ok(ApiResponse.success(platos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlatoDTO>> create(@Valid @RequestBody PlatoCreateDTO dto) {
        PlatoDTO plato = platoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Plato creado exitosamente", plato));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlatoDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody PlatoUpdateDTO dto) {
        PlatoDTO plato = platoService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Plato actualizado exitosamente", plato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        platoService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Plato eliminado exitosamente", null));
    }
}
