package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.User;
import com.jve.Repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = repository.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getDni() == null) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = repository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }
}
