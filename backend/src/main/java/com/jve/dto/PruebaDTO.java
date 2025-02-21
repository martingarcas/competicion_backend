package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PruebaDTO {
    private Long idPrueba;
    private String enunciado;
    private Integer puntuacionMaxima;
    private Long idEspecialidad;
}
