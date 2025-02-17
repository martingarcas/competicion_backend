package com.jve.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Evaluacion_Item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluacionItem;

    private Integer valoracion;
    private String explicacion;

    @ManyToOne
    @JoinColumn(name = "Evaluacion_idEvaluacion")
    @JsonBackReference
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "Item_idItem")
    @JsonBackReference
    private Item item;
}
