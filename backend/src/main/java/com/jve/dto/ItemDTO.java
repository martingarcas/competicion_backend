package com.jve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long idItem;
    private String descripcion;
    private Integer peso;
    private Integer gradosConsecucion;
    private Long idPrueba;
}