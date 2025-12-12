package com.restaurante.backend.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurante.backend.dto.UsuarioDTO;
import com.restaurante.backend.dto.auth.AuthResponse;
import com.restaurante.backend.dto.auth.LoginRequest;
import com.restaurante.backend.dto.auth.RegisterRequest;
import com.restaurante.backend.exception.BadRequestException;
import com.restaurante.backend.exception.DuplicateResourceException;
import com.restaurante.backend.mapper.UsuarioMapper;
import com.restaurante.backend.models.RefreshToken;
import com.restaurante.backend.models.Usuario;
import com.restaurante.backend.repository.RefreshTokenRepository;
import com.restaurante.backend.repository.UsuarioRepository;
import com.restaurante.backend.security.JwtService;
import com.restaurante.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        String accessToken = jwtService.generateToken(usuario);
        String refreshToken = createRefreshToken(usuario);

        return buildAuthResponse(accessToken, refreshToken, usuario);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", request.getEmail());
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        usuario = usuarioRepository.save(usuario);

        String accessToken = jwtService.generateToken(usuario);
        String refreshToken = createRefreshToken(usuario);

        return buildAuthResponse(accessToken, refreshToken, usuario);
    }

    @Override
    public AuthResponse refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Refresh token inválido"));

        if (refreshToken.getRevoked()) {
            throw new BadRequestException("El refresh token ha sido revocado");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            throw new BadRequestException("El refresh token ha expirado");
        }

        Usuario usuario = refreshToken.getUsuario();
        String accessToken = jwtService.generateToken(usuario);
        
        // Revocar el token anterior y crear uno nuevo
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
        String newRefreshToken = createRefreshToken(usuario);

        return buildAuthResponse(accessToken, newRefreshToken, usuario);
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Refresh token inválido"));
        
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    private String createRefreshToken(Usuario usuario) {
        // Revocar tokens anteriores del usuario
        refreshTokenRepository.revokeAllUserTokens(usuario.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .usuario(usuario)
                .expiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .revoked(false)
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    private AuthResponse buildAuthResponse(String accessToken, String refreshToken, Usuario usuario) {
        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .usuario(usuarioDTO)
                .build();
    }
}
