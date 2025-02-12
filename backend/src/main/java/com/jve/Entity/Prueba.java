package com.jve.Entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

@Entity
@Table(name = "Prueba")
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrueba;

    private String enunciado;
    private Integer puntuacionMaxima;

    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad")
    @JsonBackReference
    private Especialidad especialidad;

    @OneToMany(mappedBy = "prueba")
    private List<Item> items;

    public Prueba() {}

    public Prueba(Long idPrueba, String enunciado, Integer puntuacionMaxima, Especialidad especialidad) {
        this.idPrueba = idPrueba;
        this.enunciado = enunciado;
        this.puntuacionMaxima = puntuacionMaxima;
        this.especialidad = especialidad;
    }

    public Long getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Long idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return idPrueba + " - " + enunciado;
    }
}
