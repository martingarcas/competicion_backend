package com.jve.converter;

import com.jve.Entity.Prueba;
import com.jve.dto.PruebaDTO;
import org.springframework.stereotype.Component;

@Component
public class PruebaConverter {

    public PruebaDTO toDTO(Prueba prueba) {
        return new PruebaDTO(
                prueba.getIdPrueba(),
                prueba.getEnunciado(),
                prueba.getPuntuacionMaxima(),
                (prueba.getEspecialidad() != null) ? prueba.getEspecialidad().getIdEspecialidad() : null
        );
    }

    public Prueba toEntity(PruebaDTO dto) {
        Prueba prueba = new Prueba();
        prueba.setIdPrueba(dto.getIdPrueba());
        prueba.setEnunciado(dto.getEnunciado());
        prueba.setPuntuacionMaxima(dto.getPuntuacionMaxima());
        return prueba;
    }
}
