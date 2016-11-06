package com.alex.multitask;

import com.alex.multitask.users.User;
import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
@RestController
public class WebControllerUsers {
    @Autowired
    private UsersComponentDB db;

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public User addUser(@RequestParam(value = "name") String name) {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        if (db.findByName(name) != null) {
            throw new ConflictException();
        }
        return db.addUser(name);
    }

    @RequestMapping(value = "/users/byId", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam(value = "usedId") int usedId,
                           @RequestParam(value = "id") int id) {
        User usedUser = db.findById(usedId);
        if (usedUser == null) {
            throw new NotFoundException();
        }
        if (db.findById(id) == null) {
            throw new NotFoundException();
        }
        if (usedId != id && !usersService.isUserAdmin(usedUser)) {
            throw new BadRequestException();
        }

        db.deleteUser(id);
    }

    @RequestMapping(value = "/users/restore/byId", method = RequestMethod.POST)
    public void recoveryUser(@RequestParam(value = "usedId") int usedId,
                             @RequestParam(value = "id") int id) {
        User usedUser = db.findById(usedId);
        if (usedUser == null) {
            throw new NotFoundException();
        }
        if (db.findById(id) == null) {
            throw new NotFoundException();
        }
        if (usedId != id && !usersService.isUserAdmin(usedUser)) {
            throw new BadRequestException();
        }

        db.recoveryUser(id);
    }

    @RequestMapping(value = "/users/rename", method = RequestMethod.POST)
    public User renameUser(@RequestParam(value = "usedId") int usedId,
                           @RequestParam(value = "id") int id,
                           @RequestParam(value = "name") String name) {
        User usedUser = db.findById(usedId);
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        if (usedUser == null) {
            throw new NotFoundException();
        }
        if (db.findById(id) == null) {
            throw new NotFoundException();
        }
        if (db.findByName(name) != null) {
            throw new ConflictException();
        }
        if (usedId != id && !usersService.isUserAdmin(usedUser)) {
            throw new BadRequestException();
        }

        return db.renameUser(id, name);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestParam(value = "usedId") int usedId) {
        User usedUser = db.findById(usedId);
        if (usedUser == null) {
            throw new NotFoundException();
        }

        return usersService.getUsers(usedUser);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public class ConflictException extends RuntimeException {
    }
}
