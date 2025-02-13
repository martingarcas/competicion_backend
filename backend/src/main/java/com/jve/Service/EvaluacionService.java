package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.Evaluacion;
import com.jve.Repository.EvaluacionRepository;
import lombok.AllArgsConstructor;

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
        if (evaluacion.getNotaFinal() == null) {
            return null;
        }
        return repository.save(evaluacion);
    }
}
