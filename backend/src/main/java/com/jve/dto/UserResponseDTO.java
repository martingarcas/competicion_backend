package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long idUser;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String role;
    private Long especialidadId;
}
