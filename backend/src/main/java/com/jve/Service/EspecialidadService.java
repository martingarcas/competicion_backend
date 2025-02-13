package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.Especialidad;
import com.jve.Repository.EspecialidadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository repository;

    public List<Especialidad> findAll() {
        return repository.findAll();
    }

    public Optional<Especialidad> findById(Long id) {
        return repository.findById(id);
    }

    public Especialidad create(Especialidad especialidad) {
        if (especialidad.getNombre() == null || especialidad.getCodigo() == null) {
            return null;
        }
        return repository.save(especialidad);
    }
}
