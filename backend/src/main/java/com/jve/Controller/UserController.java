package com.jve.Controller;

import com.jve.Service.UserService;
import com.jve.converter.UserConverter;
import com.jve.Repository.EspecialidadRepository;
import com.jve.dto.UserDTO;
import com.jve.dto.UserResponseDTO;
import com.jve.Entity.Especialidad;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        // Si el rol es nulo, lo dejamos como "experto" por defecto
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            userDTO.setRole("experto");
        }

        // Guardamos el usuario sin especialidad para admins
        Especialidad especialidad = null;
        if (userDTO.getEspecialidadId() != null) {
            especialidad = especialidadRepository.findById(userDTO.getEspecialidadId()).orElse(null);
        }

        UserDTO newUser = userService.createUser(userDTO, especialidad);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
            .stream()
            .map(userConverter::toResponseDTO) // 🔹 Convertimos a UserResponseDTO
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userConverter::toResponseDTO) // 🔹 Convertimos a UserResponseDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @GetMapping("/expertos")
    public ResponseEntity<List<UserResponseDTO>> getAllExperts() {
        List<UserResponseDTO> experts = userService.getAllExperts()
            .stream()
            .map(userConverter::toResponseDTO) // 🔹 Convertimos a UserResponseDTO
            .collect(Collectors.toList());
        return ResponseEntity.ok(experts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        Optional<UserResponseDTO> updatedUser = userService.updateUser(id, userDTO);
        return updatedUser.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
