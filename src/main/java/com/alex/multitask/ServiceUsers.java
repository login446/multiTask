package com.alex.multitask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
@Service
public class ServiceUsers {
    @Autowired
    private ComponentUsersDB db;

    public boolean isUserAdmin(int id) {
        return db.findById(id).getLevel().equals("admin");
    }

    public List<User> getUsers(int id) {
        List<User> list = db.findAll();
        ArrayList<User> listForUser = new ArrayList<User>(list.size());
        if (isUserAdmin(id))
            return list;
        for (User user : list) {
            if (!user.isDelete())
                listForUser.add(user);
        }
        return listForUser;
    }
}
