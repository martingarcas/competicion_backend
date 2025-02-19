package com.jve.Service;

import com.jve.Repository.UserRepository;
import com.jve.converter.UserConverter;
import com.jve.dto.UserDTO;
import com.jve.Entity.User;
import com.jve.Entity.Especialidad;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

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
}
