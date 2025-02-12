package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Prueba;
import com.jve.Repository.PruebaRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
@AllArgsConstructor
public class PruebaController {

    private final PruebaRepository repository;

    @GetMapping
    public ResponseEntity<List<Prueba>> findAll() {
        List<Prueba> pruebas = repository.findAll();
        return pruebas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pruebas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prueba> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prueba> create(@RequestBody Prueba prueba) {
        if (prueba.getEnunciado() == null || prueba.getPuntuacionMaxima() == null) {
            return ResponseEntity.badRequest().build();
        }
        Prueba savedPrueba = repository.save(prueba);
        return ResponseEntity.status(201).body(savedPrueba);
    }
}
