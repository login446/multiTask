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
        assertThat(usersRepository.findOne(2).isDelete()).isFalse();
        usersService.deleteUser(2);
        assertThat(usersRepository.findOne(2).isDelete()).isTrue();

        usersService.deleteUser(99);
    }

    @Test
    public void testRecoveryUser() throws Exception {
        assertThat(usersRepository.findOne(3).isDelete()).isTrue();
        usersService.recoveryUser(3);
        assertThat(usersRepository.findOne(3).isDelete()).isFalse();

        usersService.recoveryUser(99);
    }

    @Test
    public void testRenameUser() throws Exception {
        assertThat(usersRepository.findOne(2).getName()).isEqualTo("user");
        User user = usersService.renameUser(2, "newName");
        assertThat(usersRepository.findOne(2).getId()).isEqualTo(user.getId());
        assertThat(usersRepository.findOne(2).getName()).isEqualTo(user.getName());
        assertThat(usersRepository.findOne(2).getAccessLevel()).isEqualTo(user.getAccessLevel());
    }
}