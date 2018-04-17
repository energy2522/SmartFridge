package com.smartfridge.repository;

import com.smartfridge.entity.Fridge;
import org.springframework.data.repository.CrudRepository;

public interface FridgeRepository extends CrudRepository<Fridge, Integer> {
}
