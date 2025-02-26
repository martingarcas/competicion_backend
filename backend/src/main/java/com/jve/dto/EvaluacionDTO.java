package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionDTO {

    private Long idEvaluacion;
    private Double notaFinal;
    private Long idParticipante;
    private Long idPrueba;
    private Long idUser;
    private List<EvaluacionItemDTO> evaluacionItems;

    public EvaluacionDTO(Long idEvaluacion, Long idParticipante, Long idPrueba, Long idUser, List<EvaluacionItemDTO> evaluacionItems) {
        this.idEvaluacion = idEvaluacion;
        this.idParticipante = idParticipante;
        this.idPrueba = idPrueba;
        this.idUser = idUser;
        this.evaluacionItems = evaluacionItems;
    }
}
