package com.jve.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    private String descripcion;
    private Integer peso;
    private Integer gradosConsecucion;

    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    private Prueba prueba;

    public Item() {}

    public Item(Long idItem, String descripcion, Integer peso, Integer gradosConsecucion, Prueba prueba) {
        this.idItem = idItem;
        this.descripcion = descripcion;
        this.peso = peso;
        this.gradosConsecucion = gradosConsecucion;
        this.prueba = prueba;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getGradosConsecucion() {
        return gradosConsecucion;
    }

    public void setGradosConsecucion(Integer gradosConsecucion) {
        this.gradosConsecucion = gradosConsecucion;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return idItem + " - " + descripcion;
    }
}
