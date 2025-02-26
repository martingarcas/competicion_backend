package com.jve.Service;

import com.jve.Entity.Prueba;
import com.jve.Entity.Especialidad;
import com.jve.Entity.Item;
import com.jve.Repository.PruebaRepository;
import com.jve.Repository.EspecialidadRepository;
import com.jve.Repository.ItemRepository;
import com.jve.converter.PruebaConverter;
import com.jve.dto.PruebaDTO;
import com.jve.dto.ItemDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PruebaService {

    @Autowired
    private final PruebaRepository repository;

    @Autowired
    private final PruebaConverter converter;

    @Autowired
    private final EspecialidadRepository especialidadRepository;

    @Autowired
    private final ItemRepository itemRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public List<PruebaDTO> findAll() {
        return repository.findAll().stream().map(converter::toDTO).collect(Collectors.toList());
    }

    public Optional<PruebaDTO> findById(Long id) {
        return repository.findById(id).map(converter::toDTO);
    }

    @Transactional
    public PruebaDTO create(PruebaDTO pruebaDTO, MultipartFile enunciado, List<ItemDTO> items) {
        Especialidad especialidad = especialidadRepository.findById(pruebaDTO.getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        if (enunciado == null || enunciado.isEmpty()) {
            throw new RuntimeException("No se puede crear una prueba sin un archivo de enunciado.");
        }

        try {
            // Guardar el archivo en el servidor
            String nombreArchivo = guardarArchivo(enunciado);

            Prueba prueba = new Prueba();
            prueba.setEnunciado(nombreArchivo);
            prueba.setPuntuacionMaxima(pruebaDTO.getPuntuacionMaxima());
            prueba.setEspecialidad(especialidad);

            Prueba savedPrueba = repository.save(prueba);

            if (items == null || items.isEmpty()) {
                throw new RuntimeException("Debe haber al menos un ítem para la prueba.");
            }

            int sumaPesos = items.stream().mapToInt(ItemDTO::getPeso).sum();

            if (sumaPesos != pruebaDTO.getPuntuacionMaxima()) {
                throw new RuntimeException("La suma de los pesos de los ítems (" + sumaPesos +
                        ") debe ser igual a la puntuación máxima de la prueba (" + pruebaDTO.getPuntuacionMaxima() + ").");
            }

            for (ItemDTO itemDTO : items) {
                Item item = new Item();
                item.setDescripcion(itemDTO.getDescripcion());
                item.setPeso(itemDTO.getPeso());
                item.setGradosConsecucion(itemDTO.getGradosConsecucion());
                item.setPrueba(savedPrueba);
                itemRepository.save(item);
            }

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

        return nombreArchivo;
    }

    public ResponseEntity<Resource> descargarEnunciado(Long id) {
        try {
            Optional<Prueba> pruebaOpt = repository.findById(id);
    
            if (pruebaOpt.isEmpty() || pruebaOpt.get().getEnunciado() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    
            Prueba prueba = pruebaOpt.get();
            String nombreArchivo = prueba.getEnunciado();
    
            Path path = Paths.get("uploads/" + nombreArchivo);
            Resource resource = new UrlResource(path.toUri());
    
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                    .body(resource);
    
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
