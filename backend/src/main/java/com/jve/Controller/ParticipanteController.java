package com.jve.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Obtener todos los participantes", description = "Recupera la lista completa de participantes registrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de participantes encontrada", content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "204", description = "No hay participantes registrados")
    })
    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> findAll() {
        List<ParticipanteDTO> participantes = participanteService.findAll();
        return participantes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(participantes);
    }

    @Operation(summary = "Obtener un participante por ID", description = "Recupera los datos de un participante según su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participante encontrado", content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> findById(@PathVariable Long id) {
        return participanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo participante", description = "Crea un nuevo participante con los datos proporcionados. Solo los expertos pueden realizar esta acción.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Participante creado correctamente", content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos, el nombre y el centro son obligatorios")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<?> create(@RequestBody ParticipanteDTO participanteDTO) {
        if (participanteDTO.getNombre() == null || participanteDTO.getCentro() == null) {
            return ResponseEntity.badRequest().body("El nombre y centro son obligatorios.");
        }
        ParticipanteDTO savedParticipante = participanteService.create(participanteDTO);
        return ResponseEntity.status(201).body(savedParticipante);
    }

    @Operation(summary = "Actualizar un participante", description = "Modifica los datos de un participante existente. Solo los expertos pueden realizar esta acción.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Participante actualizado correctamente", content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<ParticipanteDTO> update(@PathVariable Long id, @RequestBody ParticipanteDTO participanteDTO) {
        return participanteService.update(id, participanteDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener participantes por especialidad", description = "Recupera la lista de participantes de una especialidad específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de participantes encontrada", content = @Content(schema = @Schema(implementation = ParticipanteDTO.class))),
        @ApiResponse(responseCode = "404", description = "No hay participantes registrados en esta especialidad")
    })
    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<ParticipanteDTO>> obtenerParticipantesPorEspecialidad(@PathVariable Long id) {
        List<ParticipanteDTO> participantes = participanteService.obtenerParticipantesPorEspecialidad(id);
        return participantes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(participantes);
    }
}
