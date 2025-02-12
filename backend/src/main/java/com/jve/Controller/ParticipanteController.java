package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Participante;
import com.jve.Repository.ParticipanteRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@AllArgsConstructor
public class ParticipanteController {

    private final ParticipanteRepository repository;

    @GetMapping
    public ResponseEntity<List<Participante>> findAll() {
        List<Participante> participantes = repository.findAll();
        return participantes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participante> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Participante> create(@RequestBody Participante participante) {
        if (participante.getNombre() == null || participante.getApellidos() == null || participante.getCentro() == null) {
            return ResponseEntity.badRequest().build();
        }
        Participante savedParticipante = repository.save(participante);
        return ResponseEntity.status(201).body(savedParticipante);
    }
}
