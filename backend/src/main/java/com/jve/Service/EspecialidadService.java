package com.jve.Service;

import com.jve.Entity.Especialidad;
import com.jve.Repository.EspecialidadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        return repository.save(especialidad);
    }

    public Optional<Especialidad> update(Long id, Especialidad especialidad) {
        return repository.findById(id).map(existingEspecialidad -> {
            existingEspecialidad.setNombre(especialidad.getNombre());
            existingEspecialidad.setCodigo(especialidad.getCodigo());
            return repository.save(existingEspecialidad);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
