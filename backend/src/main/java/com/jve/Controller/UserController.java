package com.jve.Controller;

import com.jve.Service.UserService;
import com.jve.converter.UserConverter;
import com.jve.Repository.EspecialidadRepository;
import com.jve.dto.UserDTO;
import com.jve.dto.UserResponseDTO;
import com.jve.Entity.Especialidad;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EspecialidadRepository especialidadRepository;
    private final UserConverter userConverter;

    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea un nuevo usuario con el rol especificado. Si no se proporciona un rol, se asigna 'experto' por defecto."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario registrado correctamente",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            userDTO.setRole("experto");
        }

        Especialidad especialidad = null;
        if (userDTO.getEspecialidadId() != null) {
            especialidad = especialidadRepository.findById(userDTO.getEspecialidadId()).orElse(null);
        }

        UserDTO newUser = userService.createUser(userDTO, especialidad);
        return ResponseEntity.ok(newUser);
    }

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve una lista de todos los usuarios registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida correctamente",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
            .stream()
            .map(userConverter::toResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Obtener un usuario por ID",
        description = "Devuelve la información de un usuario específico según su ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userConverter::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Crea un usuario en el sistema con los datos proporcionados."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado correctamente",
            content = @Content(schema = @Schema(implementation = UserDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o especialidad no encontrada"
        )
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO.getEspecialidadId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Especialidad> especialidad = especialidadRepository.findById(userDTO.getEspecialidadId());
        if (especialidad.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.createUser(userDTO, especialidad.get()));
    }

    @Operation(
        summary = "Obtener todos los expertos",
        description = "Devuelve una lista de todos los usuarios con rol de experto."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de expertos obtenida correctamente",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
        )
    })
    @GetMapping("/expertos")
    public ResponseEntity<List<UserResponseDTO>> getAllExperts() {
        List<UserResponseDTO> experts = userService.getAllExperts()
            .stream()
            .map(userConverter::toResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(experts);
    }

    @Operation(
        summary = "Actualizar un usuario",
        description = "Modifica la información de un usuario existente identificado por su ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        Optional<UserResponseDTO> updatedUser = userService.updateUser(id, userDTO);
        return updatedUser.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
