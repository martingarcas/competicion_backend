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
}
