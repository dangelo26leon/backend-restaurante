package com.restaurante.backend.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.restaurante.backend.models.Categoria;
import com.restaurante.backend.models.Mesa;
import com.restaurante.backend.models.Plato;
import com.restaurante.backend.models.Usuario;
import com.restaurante.backend.models.enums.EstadoMesa;
import com.restaurante.backend.models.enums.Rol;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.repository.PlatoRepository;
import com.restaurante.backend.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initUsuarios();
        initCategorias();
        initMesas();
        initPlatos();
        log.info("✅ Datos de prueba inicializados correctamente");
    }

    private void initUsuarios() {
        if (usuarioRepository.count() == 0) {
            // Admin
            Usuario admin = Usuario.builder()
                    .nombre("Admin")
                    .apellido("Sistema")
                    .email("admin@restaurante.com")
                    .password(passwordEncoder.encode("admin123"))
                    .rol(Rol.ADMIN)
                    .build();
            usuarioRepository.save(admin);
            log.info("👤 Usuario Admin creado: admin@restaurante.com / admin123");

            // Mesero
            Usuario mesero = Usuario.builder()
                    .nombre("Juan")
                    .apellido("Pérez")
                    .email("mesero@restaurante.com")
                    .password(passwordEncoder.encode("mesero123"))
                    .rol(Rol.MESERO)
                    .build();
            usuarioRepository.save(mesero);
            log.info("👤 Usuario Mesero creado: mesero@restaurante.com / mesero123");

            // Cocinero
            Usuario cocinero = Usuario.builder()
                    .nombre("María")
                    .apellido("García")
                    .email("cocinero@restaurante.com")
                    .password(passwordEncoder.encode("cocinero123"))
                    .rol(Rol.COCINERO)
                    .build();
            usuarioRepository.save(cocinero);
            log.info("👤 Usuario Cocinero creado: cocinero@restaurante.com / cocinero123");
        }
    }

    private void initCategorias() {
        if (categoriaRepository.count() == 0) {
            String[] categorias = {"Entradas", "Platos Principales", "Postres", "Bebidas", "Ensaladas"};
            for (String nombre : categorias) {
                categoriaRepository.save(Categoria.builder().nombre(nombre).build());
            }
            log.info("📁 Categorías creadas: {}", categorias.length);
        }
    }

    private void initMesas() {
        if (mesaRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                mesaRepository.save(Mesa.builder()
                        .numero(i)
                        .estado(EstadoMesa.DISPONIBLE)
                        .build());
            }
            log.info("🪑 Mesas creadas: 10");
        }
    }

    private void initPlatos() {
        if (platoRepository.count() == 0) {
            Categoria entradas = categoriaRepository.findByNombre("Entradas").orElse(null);
            Categoria principales = categoriaRepository.findByNombre("Platos Principales").orElse(null);
            Categoria postres = categoriaRepository.findByNombre("Postres").orElse(null);
            Categoria bebidas = categoriaRepository.findByNombre("Bebidas").orElse(null);

            if (entradas != null) {
                platoRepository.save(Plato.builder()
                        .nombre("Empanadas (3 unidades)")
                        .precio(new BigDecimal("8.50"))
                        .descripcion("Empanadas de carne criolla")
                        .categoria(entradas)
                        .disponible(true)
                        .build());
                platoRepository.save(Plato.builder()
                        .nombre("Nachos con Guacamole")
                        .precio(new BigDecimal("12.00"))
                        .descripcion("Nachos crujientes con guacamole fresco")
                        .categoria(entradas)
                        .disponible(true)
                        .build());
            }

            if (principales != null) {
                platoRepository.save(Plato.builder()
                        .nombre("Lomo Saltado")
                        .precio(new BigDecimal("25.00"))
                        .descripcion("Lomo fino salteado con cebolla, tomate y papas fritas")
                        .categoria(principales)
                        .disponible(true)
                        .build());
                platoRepository.save(Plato.builder()
                        .nombre("Arroz con Pollo")
                        .precio(new BigDecimal("18.00"))
                        .descripcion("Arroz verde con trozos de pollo")
                        .categoria(principales)
                        .disponible(true)
                        .build());
                platoRepository.save(Plato.builder()
                        .nombre("Ceviche de Pescado")
                        .precio(new BigDecimal("28.00"))
                        .descripcion("Pescado fresco marinado en limón")
                        .categoria(principales)
                        .disponible(true)
                        .build());
            }

            if (postres != null) {
                platoRepository.save(Plato.builder()
                        .nombre("Tres Leches")
                        .precio(new BigDecimal("10.00"))
                        .descripcion("Bizcocho bañado en tres tipos de leche")
                        .categoria(postres)
                        .disponible(true)
                        .build());
                platoRepository.save(Plato.builder()
                        .nombre("Suspiro Limeño")
                        .precio(new BigDecimal("9.00"))
                        .descripcion("Postre tradicional peruano")
                        .categoria(postres)
                        .disponible(true)
                        .build());
            }

            if (bebidas != null) {
                platoRepository.save(Plato.builder()
                        .nombre("Chicha Morada")
                        .precio(new BigDecimal("5.00"))
                        .descripcion("Bebida tradicional de maíz morado")
                        .categoria(bebidas)
                        .disponible(true)
                        .build());
                platoRepository.save(Plato.builder()
                        .nombre("Pisco Sour")
                        .precio(new BigDecimal("15.00"))
                        .descripcion("Coctel peruano con pisco, limón y clara de huevo")
                        .categoria(bebidas)
                        .disponible(true)
                        .build());
            }

            log.info("🍽️ Platos de prueba creados");
        }
    }
}
