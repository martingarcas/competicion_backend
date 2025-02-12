package com.jve.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Evaluacion")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluacion;

    private Double notaFinal;

    @ManyToOne
    @JoinColumn(name = "Participante_idParticipante")
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "Prueba_idPrueba")
    private Prueba prueba;

    @ManyToOne
    @JoinColumn(name = "User_idUser")
    private User user;

    @OneToMany(mappedBy = "evaluacion")
    private List<EvaluacionItem> evaluacionItems;

    public Evaluacion() {}

    public Evaluacion(Long idEvaluacion, Double notaFinal, Participante participante, Prueba prueba, User user) {
        this.idEvaluacion = idEvaluacion;
        this.notaFinal = notaFinal;
        this.participante = participante;
        this.prueba = prueba;
        this.user = user;
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return idEvaluacion + " - Nota: " + notaFinal;
    }
}
