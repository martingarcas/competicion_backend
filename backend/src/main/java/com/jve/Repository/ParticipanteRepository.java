package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}
