package com.jve.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jve.Service.EspecialidadService;
import com.jve.Repository.EspecialidadRepository;
import com.jve.converter.UserConverter;
import com.jve.dto.EspecialidadDTO;
import com.jve.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@AllArgsConstructor
@Tag(name = "Especialidades", description = "Gestión de especialidades")
public class EspecialidadController {
    
    private final EspecialidadService especialidadService;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    private final UserConverter userConverter;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EXPERTO')")
    @GetMapping
    @Operation(summary = "Obtener todas las especialidades", description = "Devuelve una lista de todas las especialidades registradas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay especialidades registradas")
    })
    public ResponseEntity<List<EspecialidadDTO>> findAll() {
        List<EspecialidadDTO> especialidades = especialidadService.findAll();
        return especialidades.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una especialidad por ID", description = "Devuelve la información de una especialidad específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<EspecialidadDTO> findById(@PathVariable Long id) {
        return especialidadService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    @Operation(summary = "Crear una nueva especialidad", description = "Permite al administrador registrar una nueva especialidad.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Especialidad creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o especialidad ya existente")
    })
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
    @Operation(summary = "Actualizar una especialidad", description = "Permite actualizar los datos de una especialidad existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Especialidad actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<EspecialidadDTO> update(@PathVariable Long id, @RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.update(id, especialidadDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/usuarios")
    @Operation(summary = "Obtener usuarios por especialidad", description = "Devuelve una lista de usuarios asociados a una especialidad específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<List<UserResponseDTO>> obtenerUsuariosPorEspecialidad(@PathVariable Long id) {
        return especialidadService.findById(id)
                .map(especialidad -> ResponseEntity.ok(
                        especialidadService.obtenerUsuariosPorEspecialidad(id)
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
