package com.jve.Entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Participante")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipante;

    private String nombre;
    private String apellidos;
    private String centro;

    @ManyToOne
    @JoinColumn(name = "Especialidad_idEspecialidad")
    @JsonBackReference
    private Especialidad especialidad;

    public Participante() {}

    public Participante(Long idParticipante, String nombre, String apellidos, String centro, Especialidad especialidad) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.centro = centro;
        this.especialidad = especialidad;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return idParticipante + " - " + nombre + " " + apellidos;
    }
}
