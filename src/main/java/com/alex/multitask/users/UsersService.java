package com.alex.multitask.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
@Service
public class UsersService {
    @Autowired
    private UsersRepository repository;

    public boolean isUserAdmin(User user) {
        return user != null && user.getAccessLevel() == AccessLevel.ADMIN;
    }

    public List<User> getUsers(User user) {
        List<User> list = repository.findAll();
        ArrayList<User> listForUser = new ArrayList<User>();
        if (isUserAdmin(user)) {
            return list;
        }
        for (User temp : list) {
            if (!temp.isDelete()) {
                listForUser.add(temp);
            }
        }
        return listForUser;
    }

    public void deleteUser(User user) {
        if (user == null) {
            return;
        }
        user.setDelete(true);
        repository.save(user);
    }

    public void recoveryUser(User user) {
        if (user == null) {
            return;
        }
        user.setDelete(false);
        repository.save(user);
    }

    public User renameUser(User user, String name) {
        if (user == null) {
            return null;
        }
        user.setName(name);
        return repository.save(user);
    }
}
