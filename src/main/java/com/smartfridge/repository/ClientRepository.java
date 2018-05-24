package com.smartfridge.repository;

import com.smartfridge.entity.Client;
import com.smartfridge.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    Client findByIdAndUser(Integer id, User user);
}
