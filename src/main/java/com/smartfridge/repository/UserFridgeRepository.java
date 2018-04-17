package com.smartfridge.repository;

import com.smartfridge.entity.User;
import com.smartfridge.entity.UserFridge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFridgeRepository extends CrudRepository<UserFridge, Integer> {

    List<UserFridge> findByUserUsername(String username);
}
