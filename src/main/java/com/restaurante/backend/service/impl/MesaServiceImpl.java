package com.restaurante.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.backend.dto.MesaCreateDTO;
import com.restaurante.backend.dto.MesaDTO;
import com.restaurante.backend.exception.DuplicateResourceException;
import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.mapper.MesaMapper;
import com.restaurante.backend.models.Mesa;
import com.restaurante.backend.models.enums.EstadoMesa;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.service.MesaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MesaDTO> findAll() {
        return mesaRepository.findAll().stream()
                .map(mesaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MesaDTO findById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa", "id", id));
        return mesaMapper.toDTO(mesa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MesaDTO> findDisponibles() {
        return mesaRepository.findByEstado(EstadoMesa.DISPONIBLE).stream()
                .map(mesaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MesaDTO create(MesaCreateDTO dto) {
        if (mesaRepository.existsByNumero(dto.getNumero())) {
            throw new DuplicateResourceException("Mesa", "numero", dto.getNumero());
        }

        Mesa mesa = Mesa.builder()
                .numero(dto.getNumero())
                .estado(dto.getEstado() != null ? dto.getEstado() : EstadoMesa.DISPONIBLE)
                .build();

        mesa = mesaRepository.save(mesa);
        return mesaMapper.toDTO(mesa);
    }

    @Override
    public MesaDTO update(Long id, MesaCreateDTO dto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa", "id", id));

        if (dto.getNumero() != null && !dto.getNumero().equals(mesa.getNumero())) {
            if (mesaRepository.existsByNumero(dto.getNumero())) {
                throw new DuplicateResourceException("Mesa", "numero", dto.getNumero());
            }
            mesa.setNumero(dto.getNumero());
        }
        if (dto.getEstado() != null) {
            mesa.setEstado(dto.getEstado());
        }

        mesa = mesaRepository.save(mesa);
        return mesaMapper.toDTO(mesa);
    }

    @Override
    public void delete(Long id) {
        if (!mesaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mesa", "id", id);
        }
        mesaRepository.deleteById(id);
    }
}
