package com.jve.Controller;

import com.jve.Service.EvaluacionService;
import com.jve.dto.EvaluacionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Operation(summary = "Obtener todas las Evaluaciones")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Evaluaciones obtenida con éxito"),
            @ApiResponse(responseCode = "204", description = "No hay Evaluaciones disponibles")
    })
    @GetMapping
    public ResponseEntity<List<EvaluacionDTO>> findAll() {
        List<EvaluacionDTO> evaluaciones = evaluacionService.findAll();
        return evaluaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(evaluaciones);
    }

    @Operation(summary = "Obtener una Evaluacion por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> findById(@PathVariable Long id) {
        return evaluacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva Evaluacion")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    })
    @PostMapping
    public ResponseEntity<EvaluacionDTO> create(@RequestBody EvaluacionDTO evaluacionDTO) {
        try {
            // Validar si el DTO tiene los campos necesarios
            if (evaluacionDTO == null || evaluacionDTO.getIdParticipante() == null || evaluacionDTO.getIdPrueba() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            EvaluacionDTO savedEvaluacion = evaluacionService.create(evaluacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Error más específico
        }
    }
}
