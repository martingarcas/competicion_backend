package com.jve.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String role;
    private Long especialidadId;
}
