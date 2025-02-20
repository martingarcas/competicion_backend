package com.jve.Service;

import com.jve.Repository.EspecialidadRepository;
import com.jve.Repository.UserRepository;
import com.jve.converter.UserConverter;
import com.jve.dto.UserDTO;
import com.jve.dto.UserResponseDTO;
import com.jve.Entity.User;
import com.jve.Entity.Especialidad;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadRepository especialidadRepository;

    public List<User> getAllUsers() {  // 🔹 Devuelve User en vez de UserDTO
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {  // 🔹 Devuelve Optional<User>
        return userRepository.findById(id);
    }

    public UserDTO createUser(UserDTO userDTO, Especialidad especialidad) {
        User user = userConverter.toEntity(userDTO);
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("experto");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEspecialidad(especialidad); // Asigna la especialidad al usuario
        User savedUser = userRepository.save(user);
        return userConverter.toDTO(savedUser);
    }

    public List<User> getAllExperts() {
        return userRepository.findByRole("experto");  // 🔹 Filtrar solo expertos
    }

    public Optional<UserResponseDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setNombre(userDTO.getNombre());
            user.setApellidos(userDTO.getApellidos());
            user.setEmail(userDTO.getEmail());

            if (userDTO.getEspecialidadId() != null) {
                especialidadRepository.findById(userDTO.getEspecialidadId())
                        .ifPresent(user::setEspecialidad);
            }

            userRepository.save(user);
            return userConverter.toResponseDTO(user);
        });
    }
}
