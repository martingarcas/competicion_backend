package com.jve.converter;

import com.jve.Entity.EvaluacionItem;
import com.jve.dto.EvaluacionItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionItemConverter {

    @Autowired
    private ItemConverter itemConverter;

    public EvaluacionItemDTO toDTO(EvaluacionItem evaluacionItem) {
        return new EvaluacionItemDTO(
                evaluacionItem.getIdEvaluacionItem(),
                evaluacionItem.getValoracion(),
                evaluacionItem.getExplicacion(),
                evaluacionItem.getEvaluacion() != null ? evaluacionItem.getEvaluacion().getIdEvaluacion() : null,
                evaluacionItem.getItem() != null ? evaluacionItem.getItem().getIdItem() : null
        );
    }

    public EvaluacionItem toEntity(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = new EvaluacionItem();
        evaluacionItem.setIdEvaluacionItem(dto.getIdEvaluacionItem());
        evaluacionItem.setValoracion(dto.getValoracion());
        evaluacionItem.setExplicacion(dto.getExplicacion());
        // Si es necesario puedes agregar más lógica aquí para manejar la relación con `Item`
        return evaluacionItem;
    }
}
