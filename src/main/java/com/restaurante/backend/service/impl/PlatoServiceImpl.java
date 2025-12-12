package com.restaurante.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.backend.dto.PlatoCreateDTO;
import com.restaurante.backend.dto.PlatoDTO;
import com.restaurante.backend.dto.PlatoUpdateDTO;
import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.mapper.PlatoMapper;
import com.restaurante.backend.models.Categoria;
import com.restaurante.backend.models.Plato;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.repository.PlatoRepository;
import com.restaurante.backend.service.PlatoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PlatoMapper platoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> findAll() {
        return platoRepository.findAll().stream()
                .map(platoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlatoDTO findById(Long id) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato", "id", id));
        return platoMapper.toDTO(plato);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> findByCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoría", "id", categoriaId);
        }
        return platoRepository.findByCategoriaId(categoriaId).stream()
                .map(platoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoDTO> findDisponibles() {
        return platoRepository.findByDisponible(true).stream()
                .map(platoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatoDTO create(PlatoCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", dto.getCategoriaId()));

        Plato plato = Plato.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .descripcion(dto.getDescripcion())
                .categoria(categoria)
                .disponible(dto.getDisponible() != null ? dto.getDisponible() : true)
                .build();

        plato = platoRepository.save(plato);
        return platoMapper.toDTO(plato);
    }

    @Override
    public PlatoDTO update(Long id, PlatoUpdateDTO dto) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato", "id", id));

        if (dto.getNombre() != null) {
            plato.setNombre(dto.getNombre());
        }
        if (dto.getPrecio() != null) {
            plato.setPrecio(dto.getPrecio());
        }
        if (dto.getDescripcion() != null) {
            plato.setDescripcion(dto.getDescripcion());
        }
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", dto.getCategoriaId()));
            plato.setCategoria(categoria);
        }
        if (dto.getDisponible() != null) {
            plato.setDisponible(dto.getDisponible());
        }

        plato = platoRepository.save(plato);
        return platoMapper.toDTO(plato);
    }

    @Override
    public void delete(Long id) {
        if (!platoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plato", "id", id);
        }
        platoRepository.deleteById(id);
    }
}
