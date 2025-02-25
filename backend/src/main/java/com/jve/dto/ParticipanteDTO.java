package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDTO {
    private Long idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;
    private Long especialidadId;
    private String especialidadNombre;
}