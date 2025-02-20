package com.jve.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Especialidad;
import com.jve.Entity.User;
import com.jve.Repository.EspecialidadRepository;
import com.jve.Service.EspecialidadService;
import com.jve.converter.UserConverter;
import com.jve.dto.EspecialidadDTO;
import com.jve.dto.UserResponseDTO;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especialidades")
@AllArgsConstructor
public class EspecialidadController {
    
    private final EspecialidadService especialidadService;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    private final UserConverter userConverter;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EXPERTO')")
    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> findAll() {
        List<EspecialidadDTO> especialidades = especialidadService.findAll();
        return especialidades.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> findById(@PathVariable Long id) {
        return especialidadService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> create(@RequestBody EspecialidadDTO especialidadDTO) {
        if (especialidadDTO.getNombre() == null || especialidadDTO.getCodigo() == null) {
            return ResponseEntity.badRequest().body("El nombre y código son obligatorios.");
        }
        
        if (especialidadService.existsByNombre(especialidadDTO.getNombre())) {
            return ResponseEntity.badRequest().body("Ya existe una especialidad con este nombre.");
        }
        if (especialidadService.existsByCodigo(especialidadDTO.getCodigo())) {
            return ResponseEntity.badRequest().body("Ya existe una especialidad con este código.");
        }

        EspecialidadDTO savedEspecialidad = especialidadService.create(especialidadDTO);
        return ResponseEntity.status(201).body(savedEspecialidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> update(@PathVariable Long id, @RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.update(id, especialidadDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<List<UserResponseDTO>> obtenerUsuariosPorEspecialidad(@PathVariable Long id) {
        return especialidadService.findById(id)
                .map(especialidad -> ResponseEntity.ok(
                        especialidadService.obtenerUsuariosPorEspecialidad(id)
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}