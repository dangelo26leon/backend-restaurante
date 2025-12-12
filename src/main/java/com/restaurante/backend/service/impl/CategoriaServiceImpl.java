package com.restaurante.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.backend.dto.CategoriaCreateDTO;
import com.restaurante.backend.dto.CategoriaDTO;
import com.restaurante.backend.exception.DuplicateResourceException;
import com.restaurante.backend.exception.ResourceNotFoundException;
import com.restaurante.backend.mapper.CategoriaMapper;
import com.restaurante.backend.models.Categoria;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));
        return categoriaMapper.toDTO(categoria);
    }

    @Override
    public CategoriaDTO create(CategoriaCreateDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Categoría", "nombre", dto.getNombre());
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .build();

        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(categoria);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));

        if (dto.getNombre() != null && !dto.getNombre().equals(categoria.getNombre())) {
            if (categoriaRepository.existsByNombre(dto.getNombre())) {
                throw new DuplicateResourceException("Categoría", "nombre", dto.getNombre());
            }
            categoria.setNombre(dto.getNombre());
        }

        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(categoria);
    }

    @Override
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría", "id", id);
        }
        categoriaRepository.deleteById(id);
    }
}
