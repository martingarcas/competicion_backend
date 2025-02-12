package com.jve.Entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "Especialidad")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    private String nombre;
    private String codigo;

    @OneToMany(mappedBy = "especialidad")
    @JsonManagedReference
    private List<Participante> participantes;

    @OneToMany(mappedBy = "especialidad")
    @JsonManagedReference
    private List<User> usuarios;

    @OneToMany(mappedBy = "especialidad")
    @JsonManagedReference
    private List<Prueba> pruebas;

    public Especialidad() {}

    public Especialidad(Long idEspecialidad, String nombre, String codigo) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return idEspecialidad + " - " + nombre;
    }
}
