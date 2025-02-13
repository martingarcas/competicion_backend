package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.EvaluacionItem;
import com.jve.Repository.EvaluacionItemRepository;
import lombok.AllArgsConstructor;

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
        if (evaluacionItem.getValoracion() == null) {
            return null;
        }
        return repository.save(evaluacionItem);
    }
}
