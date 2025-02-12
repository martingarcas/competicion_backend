package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.EvaluacionItem;
import com.jve.Repository.EvaluacionItemRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion-items")
@AllArgsConstructor
public class EvaluacionItemController {

    private final EvaluacionItemRepository repository;

    @GetMapping
    public ResponseEntity<List<EvaluacionItem>> findAll() {
        List<EvaluacionItem> evaluacionItems = repository.findAll();
        return evaluacionItems.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(evaluacionItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionItem> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EvaluacionItem> create(@RequestBody EvaluacionItem evaluacionItem) {
        if (evaluacionItem.getValoracion() == null) {
            return ResponseEntity.badRequest().build();
        }
        EvaluacionItem savedEvaluacionItem = repository.save(evaluacionItem);
        return ResponseEntity.status(201).body(savedEvaluacionItem);
    }
}
