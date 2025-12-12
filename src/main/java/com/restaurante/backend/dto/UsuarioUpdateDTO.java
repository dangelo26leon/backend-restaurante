package com.restaurante.backend.dto;

import com.restaurante.backend.models.enums.Rol;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {
    private String nombre;
    private String apellido;
    
    @Email(message = "El email debe ser válido")
    private String email;
    
    private String password;
    private Rol rol;
}
