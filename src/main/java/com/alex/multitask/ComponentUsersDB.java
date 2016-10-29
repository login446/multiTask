package com.alex.multitask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
@Component
public class ComponentUsersDB {
    @Autowired
    private UsersRepository repository;

    public User findById(int id) {
        return repository.findOne(id);
    }

    public User findByName(String name) {
        List<User> list = repository.findByName(name);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        Iterable<User> users = repository.findAll();
        for (User user : users)
            list.add(user);
        return list;
    }

    public User addUser(String name) {
        repository.save(new User(name));
        return findByName(name);
    }

    public void deleteUser(int id) {
        User user = findById(id);
        user.setDelete(1);
        repository.save(user);
    }

    public void recoveryUser(int id) {
        User user = findById(id);
        user.setDelete(0);
        repository.save(user);
    }

    public User renameUser(int id, String name) {
        User user = findById(id);
        user.setName(name);
        repository.save(user);
        return user;
    }
}
