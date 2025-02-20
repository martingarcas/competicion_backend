package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jve.Service.ParticipanteService;
import com.jve.dto.ParticipanteDTO;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@AllArgsConstructor
public class ParticipanteController {
    
    private final ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> findAll() {
        List<ParticipanteDTO> participantes = participanteService.findAll();
        return participantes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> findById(@PathVariable Long id) {
        return participanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<?> create(@RequestBody ParticipanteDTO participanteDTO) {
        if (participanteDTO.getNombre() == null || participanteDTO.getCentro() == null) {
            return ResponseEntity.badRequest().body("El nombre y centro son obligatorios.");
        }
        ParticipanteDTO savedParticipante = participanteService.create(participanteDTO);
        return ResponseEntity.status(201).body(savedParticipante);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<ParticipanteDTO> update(@PathVariable Long id, @RequestBody ParticipanteDTO participanteDTO) {
        return participanteService.update(id, participanteDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<ParticipanteDTO>> obtenerParticipantesPorEspecialidad(@PathVariable Long id) {
        List<ParticipanteDTO> participantes = participanteService.obtenerParticipantesPorEspecialidad(id);
        return participantes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(participantes);
    }
}
