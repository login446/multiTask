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
    private UsersComponentDB db;

    public boolean isUserAdmin(User user) {
        return user != null && user.getAccessLevel() == AccessLevel.ADMIN;
    }

    public List<User> getUsers(User user) {
        List<User> list = db.findAll();
        ArrayList<User> listForUser = new ArrayList<User>();
        if (isUserAdmin(user))
            return list;
        for (User temp : list) {
            if (!temp.isDelete())
                listForUser.add(temp);
        }
        return listForUser;
    }
}
