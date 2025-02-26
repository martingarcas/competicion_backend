package com.jve.Service;

import com.jve.Entity.Evaluacion;
import com.jve.Entity.EvaluacionItem;
import com.jve.Entity.Item;
import com.jve.Repository.EvaluacionItemRepository;
import com.jve.Repository.EvaluacionRepository;
import com.jve.Repository.ItemRepository;
import com.jve.converter.EvaluacionItemConverter;
import com.jve.dto.EvaluacionItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private EvaluacionItemConverter evaluacionItemConverter;

    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemRepository.findAll().stream()
                .map(evaluacionItemConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EvaluacionItemDTO> findById(Long id) {
        return evaluacionItemRepository.findById(id)
                .map(evaluacionItemConverter::toDTO);
    }

    public EvaluacionItemDTO create(EvaluacionItemDTO evaluacionItemDTO) {
        // Obtener la Evaluacion y el Item desde sus respectivos repositorios
        Optional<Evaluacion> evaluacionOptional = evaluacionRepository.findById(evaluacionItemDTO.getIdEvaluacion());
        Optional<Item> itemOptional = itemRepository.findById(evaluacionItemDTO.getIdItem());

        if (evaluacionOptional.isPresent() && itemOptional.isPresent()) {
            Evaluacion evaluacion = evaluacionOptional.get();
            Item item = itemOptional.get();

            // Crear el EvaluacionItem
            EvaluacionItem evaluacionItem = evaluacionItemConverter.toEntity(evaluacionItemDTO);

            // Asignar la Evaluacion y el Item a EvaluacionItem
            evaluacionItem.setEvaluacion(evaluacion);
            evaluacionItem.setItem(item);

            // Guardar el EvaluacionItem
            EvaluacionItem savedEvaluacionItem = evaluacionItemRepository.save(evaluacionItem);
            
            return evaluacionItemConverter.toDTO(savedEvaluacionItem);
        } else {
            // Si no se encuentra Evaluacion o Item, devolver null o lanzar excepción
            return null;
        }
    }
}
