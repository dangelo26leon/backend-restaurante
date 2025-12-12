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
import com.restaurante.backend.dto.MesaCreateDTO;
import com.restaurante.backend.dto.MesaDTO;
import com.restaurante.backend.service.MesaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MesaDTO>>> findAll() {
        List<MesaDTO> mesas = mesaService.findAll();
        return ResponseEntity.ok(ApiResponse.success(mesas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MesaDTO>> findById(@PathVariable Long id) {
        MesaDTO mesa = mesaService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(mesa));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<MesaDTO>>> findDisponibles() {
        List<MesaDTO> mesas = mesaService.findDisponibles();
        return ResponseEntity.ok(ApiResponse.success(mesas));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MesaDTO>> create(@Valid @RequestBody MesaCreateDTO dto) {
        MesaDTO mesa = mesaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Mesa creada exitosamente", mesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MesaDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody MesaCreateDTO dto) {
        MesaDTO mesa = mesaService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Mesa actualizada exitosamente", mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        mesaService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Mesa eliminada exitosamente", null));
    }
}
