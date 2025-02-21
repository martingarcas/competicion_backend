package com.jve.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Prueba")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrueba;

    @Column(nullable = false)
    private String enunciado;

    private Integer puntuacionMaxima;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    @JsonBackReference
    private Especialidad especialidad;

    @OneToMany(mappedBy = "prueba", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "prueba", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Evaluacion> evaluaciones = new ArrayList<>();
}
