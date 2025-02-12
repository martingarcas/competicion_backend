package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Evaluacion;
import com.jve.Repository.EvaluacionRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@AllArgsConstructor
public class EvaluacionController {

    private final EvaluacionRepository repository;

    @GetMapping
    public ResponseEntity<List<Evaluacion>> findAll() {
        List<Evaluacion> evaluaciones = repository.findAll();
        return evaluaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evaluacion> create(@RequestBody Evaluacion evaluacion) {
        if (evaluacion.getNotaFinal() == null) {
            return ResponseEntity.badRequest().build();
        }
        Evaluacion savedEvaluacion = repository.save(evaluacion);
        return ResponseEntity.status(201).body(savedEvaluacion);
    }
}
