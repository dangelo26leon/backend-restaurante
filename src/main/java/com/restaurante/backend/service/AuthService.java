package com.restaurante.backend.service;

import com.restaurante.backend.dto.auth.AuthResponse;
import com.restaurante.backend.dto.auth.LoginRequest;
import com.restaurante.backend.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}
