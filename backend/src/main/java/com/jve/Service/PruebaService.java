package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.Prueba;
import com.jve.Repository.PruebaRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PruebaService {

    private final PruebaRepository repository;

    public List<Prueba> findAll() {
        return repository.findAll();
    }

    public Optional<Prueba> findById(Long id) {
        return repository.findById(id);
    }

    public Prueba create(Prueba prueba) {
        if (prueba.getEnunciado() == null || prueba.getPuntuacionMaxima() == null) {
            return null;
        }
        return repository.save(prueba);
    }
}
