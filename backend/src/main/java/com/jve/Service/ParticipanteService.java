package com.jve.Service;

import com.jve.Entity.Especialidad;
import com.jve.Entity.Participante;
import com.jve.Repository.EspecialidadRepository;
import com.jve.Repository.ParticipanteRepository;
import com.jve.converter.ParticipanteConverter;
import com.jve.dto.ParticipanteDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipanteService {

    @Autowired
    private final ParticipanteRepository repository;

    @Autowired
    private final ParticipanteConverter converter;

    @Autowired
    private final EspecialidadRepository especialidadRepository;

    public List<ParticipanteDTO> findAll() {
        return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
    }

    public Optional<ParticipanteDTO> findById(Long id) {
        return repository.findById(id).map(converter::toDTO);
    }

    @Transactional
    public ParticipanteDTO create(ParticipanteDTO participanteDTO) {
        Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        Participante participante = new Participante();
        participante.setNombre(participanteDTO.getNombre());
        participante.setApellidos(participanteDTO.getApellidos());
        participante.setCentro(participanteDTO.getCentro());
        participante.setEspecialidad(especialidad); // Aquí asignamos la especialidad encontrada

        Participante savedParticipante = repository.save(participante);
        return converter.toDTO(savedParticipante);
    }


    @Transactional
    public Optional<ParticipanteDTO> update(Long id, ParticipanteDTO participanteDTO) {
        return repository.findById(id).map(existingParticipante -> {
            existingParticipante.setNombre(participanteDTO.getNombre());
            existingParticipante.setApellidos(participanteDTO.getApellidos());
            existingParticipante.setCentro(participanteDTO.getCentro());

            // Buscar la especialidad antes de asignarla
            if (participanteDTO.getEspecialidadId() != null) {
                Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidadId())
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
                existingParticipante.setEspecialidad(especialidad);
            }

            Participante updatedParticipante = repository.save(existingParticipante);
            return converter.toDTO(updatedParticipante);
        });
    }

    public List<ParticipanteDTO> obtenerParticipantesPorEspecialidad(Long especialidadId) {
        return repository.findByEspecialidad_IdEspecialidad(especialidadId).stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }
}
