package com.jve.converter;

import com.jve.dto.ItemDTO;
import com.jve.Entity.Item;
import com.jve.Entity.Prueba;

import org.springframework.stereotype.Component;

@Component
public class ItemConverter {
    public ItemDTO entityToDTO(Item item) {
        return new ItemDTO(
                item.getIdItem(),
                item.getDescripcion(),
                item.getPeso(),
                item.getGradosConsecucion(),
                item.getPrueba() != null ? item.getPrueba().getIdPrueba() : null
        );
    }

    public Item dtoToEntity(ItemDTO itemDTO, Prueba prueba) {
        Item item = new Item();
        item.setIdItem(itemDTO.getIdItem());
        item.setDescripcion(itemDTO.getDescripcion());
        item.setPeso(itemDTO.getPeso());
        item.setGradosConsecucion(itemDTO.getGradosConsecucion());
        item.setPrueba(prueba);
        return item;
    }
}