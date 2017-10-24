package com.example.apisessions.repositories;

import com.example.apisessions.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String name);

    User findFirstByNameAndPassword(String name, String password);
}
