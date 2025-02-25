package com.jve.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByPruebaIdPrueba(Long idPrueba);
}
