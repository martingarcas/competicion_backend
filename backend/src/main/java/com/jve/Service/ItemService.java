package com.jve.Service;

import com.jve.Entity.Item;
import com.jve.Repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        return repository.save(item);
    }

    public Optional<Item> update(Long id, Item item) {
        return repository.findById(id).map(existingItem -> {
            existingItem.setDescripcion(item.getDescripcion());
            existingItem.setPeso(item.getPeso());
            return repository.save(existingItem);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
