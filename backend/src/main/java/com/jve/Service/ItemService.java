package com.jve.Service;

import org.springframework.stereotype.Service;
import com.jve.Entity.Item;
import com.jve.Repository.ItemRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    public Item create(Item item) {
        if (item.getDescripcion() == null || item.getPeso() == null) {
            return null;
        }
        return repository.save(item);
    }
}
