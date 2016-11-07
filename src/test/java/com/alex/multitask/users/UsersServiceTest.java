package com.alex.multitask.users;

import com.alex.multitask.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/scriptUsersTest.sql")
public class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

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
        List<User> list = usersService.getUsers(usersRepository.findOne(1));

        assertEquals(3, list.size());
        assertEquals(list.get(0).getName(), "admin");
        assertEquals(list.get(1).getName(), "user");
        assertEquals(list.get(2).getName(), "userDelete");

        list = usersService.getUsers(usersRepository.findOne(2));

        assertEquals(2, list.size());
        assertEquals(list.get(0).getName(), "admin");
        assertEquals(list.get(1).getName(), "user");
    }


    @Test
    public void testDeleteUser() throws Exception {
        User user = usersRepository.findOne(2);
        assertThat(user.isDelete()).isFalse();
        usersService.deleteUser(user);
        assertThat(user.isDelete()).isTrue();

        usersService.deleteUser(usersRepository.findOne(99));
    }

    @Test
    public void testRecoveryUser() throws Exception {
        User user = usersRepository.findOne(3);
        assertThat(user.isDelete()).isTrue();
        usersService.recoveryUser(user);
        assertThat(user.isDelete()).isFalse();

        usersService.recoveryUser(usersRepository.findOne(99));
    }

    @Test
    public void testRenameUser() throws Exception {
        User user = usersRepository.findOne(2);
        assertThat(user.getName()).isEqualTo("user");
        User userNewName = usersService.renameUser(user, "newName");
        assertThat(user.getId()).isEqualTo(userNewName.getId());
        assertThat(user.getName()).isEqualTo(userNewName.getName());
        assertThat(user.getAccessLevel()).isEqualTo(userNewName.getAccessLevel());
        assertThat(user.getName()).isEqualTo("newName");
    }
}