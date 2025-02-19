package com.jve.Service;

import com.jve.Entity.Especialidad;
import com.jve.Repository.EspecialidadRepository;
import com.jve.converter.EspecialidadConverter;
import com.jve.dto.EspecialidadDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository repository;
    private final EspecialidadConverter converter;

    public List<EspecialidadDTO> findAll() {
        return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
    }

    public Optional<EspecialidadDTO> findById(Long id) {
        return repository.findById(id).map(converter::toDTO);
    }

    public EspecialidadDTO create(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = converter.toEntity(especialidadDTO);
        Especialidad savedEspecialidad = repository.save(especialidad);
        return converter.toDTO(savedEspecialidad);
    }

    @Transactional
    public Optional<EspecialidadDTO> update(Long id, EspecialidadDTO especialidadDTO) {
        return repository.findById(id).map(existingEspecialidad -> {
            existingEspecialidad.setNombre(especialidadDTO.getNombre());
            existingEspecialidad.setCodigo(especialidadDTO.getCodigo());
            Especialidad updatedEspecialidad = repository.save(existingEspecialidad);
            return converter.toDTO(updatedEspecialidad);
        });
    }
}
