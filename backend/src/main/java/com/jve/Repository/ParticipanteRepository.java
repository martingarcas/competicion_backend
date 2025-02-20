package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.Participante;
import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    List<Participante> findByEspecialidad_IdEspecialidad(Long idEspecialidad);
}
