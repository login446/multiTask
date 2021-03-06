package com.alex.multitask.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
public interface UsersRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
    List<User> findAll();
}
