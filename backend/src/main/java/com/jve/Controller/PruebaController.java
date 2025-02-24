package com.jve.Controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jve.Service.PruebaService;
import com.jve.dto.PruebaDTO;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
@AllArgsConstructor
public class PruebaController {
    
    private final PruebaService pruebaService;

    @GetMapping
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<List<PruebaDTO>> findAll() {
        List<PruebaDTO> pruebas = pruebaService.findAll();
        return pruebas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pruebas);
    }

    @PostMapping(value = "/crear", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<?> create(
            @RequestParam("especialidadId") Long especialidadId,
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima,
            @RequestParam("enunciado") MultipartFile enunciado) {
    
        if (enunciado.isEmpty()) {
            return ResponseEntity.badRequest().body("El enunciado de la prueba es obligatorio.");
        }
    
        try {
            // Crear DTO con la información
            PruebaDTO pruebaDTO = new PruebaDTO();
            pruebaDTO.setIdEspecialidad(especialidadId);
            pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
    
            PruebaDTO savedPrueba = pruebaService.create(pruebaDTO, enunciado);
            return ResponseEntity.status(201).body(savedPrueba);
    
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la prueba: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<PruebaDTO> findById(@PathVariable Long id) {
        return pruebaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/especialidad/{id}")
    @PreAuthorize("hasAuthority('experto')")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebasPorEspecialidad(@PathVariable Long id) {
        List<PruebaDTO> pruebas = pruebaService.obtenerPruebasPorEspecialidad(id);
        return pruebas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pruebas);
    }

    @GetMapping("/descargar/{id}")
    public ResponseEntity<Resource> descargarEnunciado(@PathVariable Long id) {
        return pruebaService.descargarEnunciado(id);
    }
}
