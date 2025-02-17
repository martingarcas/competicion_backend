package com.jve.Service;

import com.jve.Entity.Participante;
import com.jve.Repository.ParticipanteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        return repository.save(participante);
    }

    public Optional<Participante> update(Long id, Participante participante) {
        return repository.findById(id).map(existingParticipante -> {
            existingParticipante.setNombre(participante.getNombre());
            existingParticipante.setApellidos(participante.getApellidos());
            existingParticipante.setCentro(participante.getCentro());
            return repository.save(existingParticipante);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
