package com.jve.converter;

import com.jve.Entity.Participante;
import com.jve.dto.ParticipanteDTO;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteConverter {
    public ParticipanteDTO toDTO(Participante participante) {
        return new ParticipanteDTO(
                participante.getIdParticipante(),
                participante.getNombre(),
                participante.getApellidos(),
                participante.getCentro(),
                (participante.getEspecialidad() != null) ? participante.getEspecialidad().getIdEspecialidad() : null
        );
    }

    public Participante toEntity(ParticipanteDTO dto) {
        Participante participante = new Participante();
        participante.setIdParticipante(dto.getIdParticipante());
        participante.setNombre(dto.getNombre());
        participante.setApellidos(dto.getApellidos());
        participante.setCentro(dto.getCentro());
        return participante;
    }
}
