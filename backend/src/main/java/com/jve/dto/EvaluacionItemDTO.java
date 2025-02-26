package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionItemDTO {

    private Long idEvaluacionItem;
    private Integer valoracion;
    private String explicacion;
    private Long idEvaluacion;
    private Long idItem;
}
