package com.jve.Service;

import com.jve.Entity.Evaluacion;
import com.jve.Repository.EvaluacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository repository;

    public List<Evaluacion> findAll() {
        return repository.findAll();
    }

    public Optional<Evaluacion> findById(Long id) {
        return repository.findById(id);
    }

    public Evaluacion create(Evaluacion evaluacion) {
        return repository.save(evaluacion);
    }

    public Optional<Evaluacion> update(Long id, Evaluacion evaluacion) {
        return repository.findById(id).map(existingEvaluacion -> {
            existingEvaluacion.setNotaFinal(evaluacion.getNotaFinal());
            return repository.save(existingEvaluacion);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
