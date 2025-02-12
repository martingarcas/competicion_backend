package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.EvaluacionItem;

@Repository
public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Long> {
}
