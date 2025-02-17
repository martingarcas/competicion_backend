package com.jve.Service;

import com.jve.Entity.EvaluacionItem;
import com.jve.Repository.EvaluacionItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EvaluacionItemService {

    private final EvaluacionItemRepository repository;

    public List<EvaluacionItem> findAll() {
        return repository.findAll();
    }

    public Optional<EvaluacionItem> findById(Long id) {
        return repository.findById(id);
    }

    public EvaluacionItem create(EvaluacionItem evaluacionItem) {
        return repository.save(evaluacionItem);
    }

    public Optional<EvaluacionItem> update(Long id, EvaluacionItem evaluacionItem) {
        return repository.findById(id).map(existingEvaluacionItem -> {
            existingEvaluacionItem.setValoracion(evaluacionItem.getValoracion());
            return repository.save(existingEvaluacionItem);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
