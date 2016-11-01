package com.alex.multitask.users;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersComponentDB usersComponentDB;

    @Test
    public void testIsUserAdmin() throws Exception {
        User user = new User("test");
        user.setAccessLevel(AccessLevel.ADMIN);
        Assert.assertTrue(usersService.isUserAdmin(user));
        user.setAccessLevel(AccessLevel.USER);
        Assert.assertFalse(usersService.isUserAdmin(user));
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> list = new ArrayList<User>();
        User admin = new User("admin");
        admin.setAccessLevel(AccessLevel.ADMIN);
        User user = new User("user");
        User userDelete = new User("userDelete");
        userDelete.setDelete(true);
        list.add(admin);
        list.add(user);
        list.add(userDelete);
        when(usersComponentDB.findAll()).thenReturn(list);

        assertEquals(list.get(0), usersService.getUsers(admin).get(0));
        assertEquals(list.get(1), usersService.getUsers(admin).get(1));
        assertEquals(list.get(2), usersService.getUsers(admin).get(2));

        assertEquals(list.get(0).getName(), usersService.getUsers(user).get(0).getName());
        assertEquals(list.get(1).getName(), usersService.getUsers(user).get(1).getName());
        assertEquals(2, usersService.getUsers(user).size());
    }
}