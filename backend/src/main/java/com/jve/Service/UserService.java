package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.User;
import com.jve.Repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User create(User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getDni() == null) {
            return null;
        }
        return repository.save(user);
    }
}
