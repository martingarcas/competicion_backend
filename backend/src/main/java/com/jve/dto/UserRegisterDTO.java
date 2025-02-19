package com.jve.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/*
DTO con la información necesaria para registrar un nuevo usuario en base de datos
{
    "username": "user1",
    "password": "admin",
    "email": "user1@example.com",
    "nombre": "martin",
    "apellidos": "garcia",
    "especialidadId": 2
}
 */
public record UserRegisterDTO(
    @NotNull(message = "El nombre de usuario es obligatorio") String username,
    @NotNull(message = "La contraseña es obligatoria") String password,
    @NotNull(message = "El email es obligatorio") @Email(message = "El email debe tener un formato válido") String email,
    @NotNull(message = "El nombre es obligatorio") String nombre,
    @NotNull(message = "Los apellidos son obligatorios") String apellidos,
    @NotNull(message = "Debe seleccionar una especialidad") Long especialidadId
) {
}

