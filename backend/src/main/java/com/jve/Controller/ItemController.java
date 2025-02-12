package com.jve.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jve.Entity.Item;
import com.jve.Repository.ItemRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemController {

    private final ItemRepository repository;

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = repository.findAll();
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        if (item.getDescripcion() == null || item.getPeso() == null) {
            return ResponseEntity.badRequest().build();
        }
        Item savedItem = repository.save(item);
        return ResponseEntity.status(201).body(savedItem);
    }
}
