package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadDTO {
    private Long idEspecialidad;
    private String nombre;
    private String codigo;
}
