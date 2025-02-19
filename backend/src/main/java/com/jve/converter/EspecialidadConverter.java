package com.jve.converter;

import com.jve.Entity.Especialidad;
import com.jve.dto.EspecialidadDTO;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadConverter {
    public EspecialidadDTO toDTO(Especialidad especialidad) {
        return new EspecialidadDTO(
                especialidad.getIdEspecialidad(),
                especialidad.getNombre(),
                especialidad.getCodigo()
        );
    }

    public Especialidad toEntity(EspecialidadDTO dto) {
        Especialidad especialidad = new Especialidad();
        especialidad.setIdEspecialidad(dto.getIdEspecialidad());
        especialidad.setNombre(dto.getNombre());
        especialidad.setCodigo(dto.getCodigo());
        return especialidad;
    }
}
