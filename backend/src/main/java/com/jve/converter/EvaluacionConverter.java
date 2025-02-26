package com.jve.converter;

import com.jve.Entity.Evaluacion;
import com.jve.dto.EvaluacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EvaluacionConverter {

    @Autowired
    private EvaluacionItemConverter evaluacionItemConverter;

    public EvaluacionDTO toDTO(Evaluacion evaluacion) {
        return new EvaluacionDTO(
                evaluacion.getIdEvaluacion(),
                evaluacion.getNotaFinal(),
                evaluacion.getParticipante() != null ? evaluacion.getParticipante().getIdParticipante() : null,
                evaluacion.getPrueba() != null ? evaluacion.getPrueba().getIdPrueba() : null,
                evaluacion.getUser() != null ? evaluacion.getUser().getIdUser() : null,
                evaluacion.getEvaluacionItems().stream()
                        .map(evaluacionItemConverter::toDTO)
                        .collect(Collectors.toList())
        );
    }

    public Evaluacion toEntity(EvaluacionDTO dto) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setIdEvaluacion(dto.getIdEvaluacion());
        evaluacion.setNotaFinal(dto.getNotaFinal());
        return evaluacion;
    }
}
