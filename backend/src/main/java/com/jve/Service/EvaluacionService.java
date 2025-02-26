package com.jve.Service;

import com.jve.Entity.Evaluacion;
import com.jve.Entity.EvaluacionItem;
import com.jve.Repository.EvaluacionRepository;
import com.jve.converter.EvaluacionConverter;
import com.jve.dto.EvaluacionDTO;
import com.jve.dto.EvaluacionItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EvaluacionConverter evaluacionConverter;

    public List<EvaluacionDTO> findAll() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacionConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EvaluacionDTO> findById(Long id) {
        return evaluacionRepository.findById(id)
                .map(evaluacionConverter::toDTO);
    }

    public EvaluacionDTO create(EvaluacionDTO evaluacionDTO) {
        Evaluacion evaluacion = evaluacionConverter.toEntity(evaluacionDTO);
        Double notaFinal = calcularNotaFinal(evaluacionDTO);
        evaluacion.setNotaFinal(notaFinal);

        Evaluacion savedEvaluacion = evaluacionRepository.save(evaluacion);
        return evaluacionConverter.toDTO(savedEvaluacion);
    }

    private Double calcularNotaFinal(EvaluacionDTO evaluacionDTO) {

        if (evaluacionDTO.getEvaluacionItems() != null && !evaluacionDTO.getEvaluacionItems().isEmpty()) {
            double sumaValores = evaluacionDTO.getEvaluacionItems().stream()
                    .mapToDouble(EvaluacionItemDTO::getValoracion)
                    .sum();

            return sumaValores / evaluacionDTO.getEvaluacionItems().size();
        } else {
            return 0.0;
        }
    }
}
