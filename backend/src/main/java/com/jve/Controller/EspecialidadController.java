package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Especialidad;
import com.jve.Repository.EspecialidadRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@AllArgsConstructor
public class EspecialidadController {

    private final EspecialidadRepository repository;

    @GetMapping
    public ResponseEntity<List<Especialidad>> findAll() {
        List<Especialidad> especialidades = repository.findAll();
        return especialidades.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Especialidad> create(@RequestBody Especialidad especialidad) {
        if (especialidad.getNombre() == null || especialidad.getCodigo() == null) {
            return ResponseEntity.badRequest().build();
        }
        Especialidad savedEspecialidad = repository.save(especialidad);
        return ResponseEntity.status(201).body(savedEspecialidad);
    }
}
