package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.Prueba;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {
}
