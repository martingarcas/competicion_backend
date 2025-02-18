package com.jve.Service;

import com.jve.Repository.UserRepository;
import com.jve.converter.UserConverter;
import com.jve.dto.UserDTO;
import com.jve.Entity.User;
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

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userConverter::toDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("experto");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userConverter.toDTO(savedUser);
    }
}
