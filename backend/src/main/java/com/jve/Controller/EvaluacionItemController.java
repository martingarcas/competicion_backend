package com.jve.Controller;

import com.jve.Service.EvaluacionItemService;
import com.jve.dto.EvaluacionItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion-items")
public class EvaluacionItemController {

    @Autowired
    private EvaluacionItemService evaluacionItemService;

    @Operation(summary = "Obtener todos los EvaluacionItems")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de EvaluacionItems obtenida con éxito"),
            @ApiResponse(responseCode = "204", description = "No hay EvaluacionItems disponibles")
    })
    @GetMapping
    public ResponseEntity<List<EvaluacionItemDTO>> findAll() {
        List<EvaluacionItemDTO> evaluacionItems = evaluacionItemService.findAll();
        return evaluacionItems.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(evaluacionItems);
    }

    @Operation(summary = "Obtener un EvaluacionItem por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "EvaluacionItem encontrado"),
            @ApiResponse(responseCode = "404", description = "EvaluacionItem no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionItemDTO> findById(@PathVariable Long id) {
        return evaluacionItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo EvaluacionItem")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "EvaluacionItem creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    })
    @PostMapping
    public ResponseEntity<EvaluacionItemDTO> create(@RequestBody EvaluacionItemDTO evaluacionItemDTO) {
        try {
            // Llamada al servicio para crear el EvaluacionItem
            EvaluacionItemDTO savedEvaluacionItem = evaluacionItemService.create(evaluacionItemDTO);
            
            // Verificar si el EvaluacionItem fue creado correctamente
            if (savedEvaluacionItem == null) {
                return ResponseEntity.badRequest().body(null);  // Si no se pudo crear el EvaluacionItem
            }
            return ResponseEntity.status(201).body(savedEvaluacionItem); // Si se creó correctamente

        } catch (Exception e) {
            // Manejo de excepciones general
            return ResponseEntity.badRequest().body(null);
        }
    }
}
