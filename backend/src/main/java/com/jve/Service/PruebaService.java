package com.jve.Service;

import com.jve.Entity.Prueba;
import com.jve.Entity.Especialidad;
import com.jve.Repository.PruebaRepository;
import com.jve.Repository.EspecialidadRepository;
import com.jve.converter.PruebaConverter;
import com.jve.dto.PruebaDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

@Service
@AllArgsConstructor
public class PruebaService {

    @Autowired
    private final PruebaRepository repository;

    @Autowired
    private final PruebaConverter converter;

    @Autowired
    private final EspecialidadRepository especialidadRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public List<PruebaDTO> findAll() {
        return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
    }

    public Optional<PruebaDTO> findById(Long id) {
        return repository.findById(id).map(converter::toDTO);
    }

    @Transactional
    public PruebaDTO create(PruebaDTO pruebaDTO, MultipartFile enunciado) {
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        if (enunciado == null || enunciado.isEmpty()) {
            throw new RuntimeException("No se puede crear una prueba sin un archivo de enunciado.");
        }

        try {
            // Guardar el archivo en el servidor
            String nombreArchivo = guardarArchivo(enunciado);

            Prueba prueba = new Prueba();
            prueba.setEnunciado(nombreArchivo); // Guardar solo el nombre del archivo
            prueba.setPuntuacionMaxima(pruebaDTO.getPuntuacionMaxima());
            prueba.setEspecialidad(especialidad);

            Prueba savedPrueba = repository.save(prueba);
            return converter.toDTO(savedPrueba);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public String guardarArchivo(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
        Path rutaArchivo = Paths.get(UPLOAD_DIR).resolve(nombreArchivo);
        
        // Crear el directorio si no existe
        Files.createDirectories(rutaArchivo.getParent());
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        return nombreArchivo; // Retorna el nombre del archivo para guardar en la BD
    }

    public ResponseEntity<Resource> descargarEnunciado(Long id) {
        Optional<Prueba> pruebaOpt = repository.findById(id);
        if (pruebaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    
        String nombreArchivo = pruebaOpt.get().getEnunciado();
        Path rutaArchivo = Paths.get(UPLOAD_DIR).resolve(nombreArchivo);
        
        try {
            Resource recurso = new UrlResource(rutaArchivo.toUri());
            
            // Convertir a UrlResource antes de llamar a isReadable()
            if (!(recurso instanceof UrlResource) || !((UrlResource) recurso).isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Para soportar múltiples formatos
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                    .body(recurso);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    public List<PruebaDTO> obtenerPruebasPorEspecialidad(Long especialidadId) {
        return repository.findByEspecialidad_IdEspecialidad(especialidadId).stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }
}
