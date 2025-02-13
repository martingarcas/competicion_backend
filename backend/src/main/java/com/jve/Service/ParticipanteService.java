package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.Participante;
import com.jve.Repository.ParticipanteRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository repository;

    public List<Participante> findAll() {
        return repository.findAll();
    }

    public Optional<Participante> findById(Long id) {
        return repository.findById(id);
    }

    public Participante create(Participante participante) {
        if (participante.getNombre() == null || participante.getApellidos() == null || participante.getCentro() == null) {
            return null;
        }
        return repository.save(participante);
    }
}
