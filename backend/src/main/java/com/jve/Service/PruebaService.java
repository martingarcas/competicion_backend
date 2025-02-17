package com.jve.Service;

import com.jve.Entity.Prueba;
import com.jve.Repository.PruebaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        return repository.save(prueba);
    }

    public Optional<Prueba> update(Long id, Prueba prueba) {
        return repository.findById(id).map(existingPrueba -> {
            existingPrueba.setEnunciado(prueba.getEnunciado());
            existingPrueba.setPuntuacionMaxima(prueba.getPuntuacionMaxima());
            return repository.save(existingPrueba);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
