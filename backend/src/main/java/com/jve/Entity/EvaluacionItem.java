package com.jve.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Evaluacion_Item")
public class EvaluacionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluacionItem;

    private Integer valoracion;
    private String explicacion;

    @ManyToOne
    @JoinColumn(name = "Evaluacion_idEvaluacion")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "Item_idItem")
    private Item item;

    public EvaluacionItem() {}

    public EvaluacionItem(Long idEvaluacionItem, Integer valoracion, String explicacion, Evaluacion evaluacion, Item item) {
        this.idEvaluacionItem = idEvaluacionItem;
        this.valoracion = valoracion;
        this.explicacion = explicacion;
        this.evaluacion = evaluacion;
        this.item = item;
    }

    public Long getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Long idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return idEvaluacionItem + " - Valoración: " + valoracion;
    }
}
