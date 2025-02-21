package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.Prueba;
import java.util.List;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    List<Prueba> findByEspecialidad_IdEspecialidad(Long idEspecialidad);
}
