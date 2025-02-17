package com.jve.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Evaluacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluacion;

    private Double notaFinal;

    @ManyToOne
    @JoinColumn(name = "Participante_idParticipante")
    @JsonBackReference
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    @JsonBackReference
    private Prueba prueba;

    @ManyToOne
    @JoinColumn(name = "User_idUser")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EvaluacionItem> evaluacionItems = new ArrayList<>();
}
