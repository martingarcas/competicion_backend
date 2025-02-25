package com.jve.Service;

import com.jve.Entity.Item;
import com.jve.Entity.Prueba;
import com.jve.Repository.ItemRepository;
import com.jve.Repository.PruebaRepository;
import com.jve.converter.ItemConverter;
import com.jve.dto.ItemDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    @Autowired
    private final ItemRepository repository;

    @Autowired
    private final ItemConverter converter;

    @Autowired
    private final PruebaRepository pruebaRepository;

    public List<ItemDTO> findAll() {
        return repository.findAll().stream().map(converter::entityToDTO).collect(Collectors.toList());
    }

    public Optional<ItemDTO> findById(Long id) {
        return repository.findById(id).map(converter::entityToDTO);
    }

    @Transactional
    public ItemDTO create(ItemDTO itemDTO) {
        Prueba prueba = pruebaRepository.findById(itemDTO.getIdPrueba())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        Item item = new Item();
        item.setDescripcion(itemDTO.getDescripcion());
        item.setPeso(itemDTO.getPeso());
        item.setGradosConsecucion(itemDTO.getGradosConsecucion());
        item.setPrueba(prueba);

        Item savedItem = repository.save(item);
        return converter.entityToDTO(savedItem);
    }

    @Transactional
    public Optional<ItemDTO> update(Long id, ItemDTO itemDTO) {
        return repository.findById(id).map(existingItem -> {
            existingItem.setDescripcion(itemDTO.getDescripcion());
            existingItem.setPeso(itemDTO.getPeso());
            existingItem.setGradosConsecucion(itemDTO.getGradosConsecucion());

            if (itemDTO.getIdPrueba() != null) {
                Prueba prueba = pruebaRepository.findById(itemDTO.getIdPrueba())
                        .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));
                existingItem.setPrueba(prueba);
            }

            Item updatedItem = repository.save(existingItem);
            return converter.entityToDTO(updatedItem);
        });
    }

    public List<ItemDTO> obtenerItemsPorPrueba(Long pruebaId) {
        return repository.findByPruebaIdPrueba(pruebaId).stream()
                .map(converter::entityToDTO)
                .collect(Collectors.toList());
    }
}
