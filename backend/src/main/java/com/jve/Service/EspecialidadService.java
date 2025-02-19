package com.jve.Service;

import com.jve.Entity.Especialidad;
import com.jve.Repository.EspecialidadRepository;
import com.jve.converter.EspecialidadConverter;
import com.jve.converter.UserConverter;
import com.jve.dto.EspecialidadDTO;
import com.jve.dto.UserResponseDTO;

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
    private final UserConverter userConverter;

    public List<EspecialidadDTO> findAll() {
        return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
    }

    public Optional<EspecialidadDTO> findById(Long id) {
        return repository.findById(id).map(converter::toDTO);
    }

    public EspecialidadDTO create(EspecialidadDTO especialidadDTO) {
        if (repository.existsByNombre(especialidadDTO.getNombre())) {
            throw new RuntimeException("Ya existe una especialidad con este nombre.");
        }
        if (repository.existsByCodigo(especialidadDTO.getCodigo())) {
            throw new RuntimeException("Ya existe una especialidad con este código.");
        }

        Especialidad especialidad = converter.toEntity(especialidadDTO);
        Especialidad savedEspecialidad = repository.save(especialidad);
        return converter.toDTO(savedEspecialidad);
    }

    @Transactional
    public Optional<EspecialidadDTO> update(Long id, EspecialidadDTO especialidadDTO) {
        return repository.findById(id).map(existingEspecialidad -> {
            if (!existingEspecialidad.getNombre().equals(especialidadDTO.getNombre()) &&
                    repository.existsByNombre(especialidadDTO.getNombre())) {
                throw new RuntimeException("Ya existe otra especialidad con este nombre.");
            }

            if (!existingEspecialidad.getCodigo().equals(especialidadDTO.getCodigo()) &&
                    repository.existsByCodigo(especialidadDTO.getCodigo())) {
                throw new RuntimeException("Ya existe otra especialidad con este código.");
            }

            existingEspecialidad.setNombre(especialidadDTO.getNombre());
            existingEspecialidad.setCodigo(especialidadDTO.getCodigo());
            Especialidad updatedEspecialidad = repository.save(existingEspecialidad);
            return converter.toDTO(updatedEspecialidad);
        });
    }

    public List<UserResponseDTO> obtenerUsuariosPorEspecialidad(Long id) {
        return repository.findById(id)
                .map(especialidad -> especialidad.getUsuarios().stream()
                        .map(userConverter::toResponseDTO) // ✅ Usamos el UserConverter
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
    }
    

    public boolean existsByNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }

    public boolean existsByCodigo(String codigo) {
        return repository.existsByCodigo(codigo);
    }
}
