package com.jve.Controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jve.Service.PruebaService;
import com.jve.dto.ItemDTO;
import com.jve.dto.PruebaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
@AllArgsConstructor
public class PruebaController {
    
    private final PruebaService pruebaService;

    @Operation(
        summary = "Obtener todas las pruebas",
        description = "Devuelve una lista de todas las pruebas disponibles"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de pruebas obtenida con éxito",
            content = @Content(schema = @Schema(implementation = PruebaDTO.class))
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay pruebas disponibles"
        )
    })
    @GetMapping
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<List<PruebaDTO>> findAll() {
        List<PruebaDTO> pruebas = pruebaService.findAll();
        return pruebas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pruebas);
    }

    @Operation(
        summary = "Crear una nueva prueba",
        description = "Crea una nueva prueba con los datos proporcionados y un archivo adjunto"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Prueba creada correctamente",
            content = @Content(schema = @Schema(implementation = PruebaDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "El enunciado de la prueba es obligatorio"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al crear la prueba"
        )
    })
    @PostMapping(value = "/crear", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<?> create(
            @RequestParam("especialidadId") Long especialidadId,
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima,
            @RequestParam("enunciado") MultipartFile enunciado,
            @RequestPart(value = "items", required = false) List<ItemDTO> items) {
    
        if (enunciado.isEmpty()) {
            return ResponseEntity.badRequest().body("El enunciado de la prueba es obligatorio.");
        }
    
        try {
            PruebaDTO pruebaDTO = new PruebaDTO();
            pruebaDTO.setIdEspecialidad(especialidadId);
            pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
    
            PruebaDTO savedPrueba = pruebaService.create(pruebaDTO, enunciado, items);
            return ResponseEntity.status(201).body(savedPrueba);
    
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la prueba: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Obtener una prueba por ID",
        description = "Devuelve los detalles de una prueba específica según su ID"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Prueba encontrada",
            content = @Content(schema = @Schema(implementation = PruebaDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Prueba no encontrada"
        )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<PruebaDTO> findById(@PathVariable Long id) {
        return pruebaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener pruebas por especialidad",
        description = "Devuelve una lista de pruebas asociadas a una especialidad específica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de pruebas obtenida con éxito",
            content = @Content(schema = @Schema(implementation = PruebaDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron pruebas para la especialidad dada"
        )
    })
    @GetMapping("/especialidad/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebasPorEspecialidad(@PathVariable Long id) {
        List<PruebaDTO> pruebas = pruebaService.obtenerPruebasPorEspecialidad(id);
        return pruebas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pruebas);
    }

    @Operation(
        summary = "Descargar enunciado de una prueba",
        description = "Permite descargar el archivo del enunciado de una prueba específica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Archivo descargado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Prueba no encontrada"
        )
    })
    @GetMapping("/descargar/{id}")
    public ResponseEntity<Resource> descargarEnunciado(@PathVariable Long id) {
        return pruebaService.descargarEnunciado(id);
    }
}
