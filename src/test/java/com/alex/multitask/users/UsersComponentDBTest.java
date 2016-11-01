package com.alex.multitask.users;

import com.alex.multitask.Application;
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

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/scriptUsersTest.sql")
public class UsersComponentDBTest {

    @Autowired
    UsersComponentDB componentDB;

    @Test
    public void testFindById() throws Exception {
        User user = componentDB.findById(1);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("admin");
        assertThat(user.getAccessLevel()).isEqualTo(AccessLevel.ADMIN);

        assertThat(componentDB.findById(993)).isEqualTo(null);
    }

    @Test
    public void testFindByName() throws Exception {
        User user = componentDB.findByName("user");

        assertThat(user.getId()).isEqualTo(2);
        assertThat(user.getName()).isEqualTo("user");
        assertThat(user.getAccessLevel()).isEqualTo(AccessLevel.USER);

        assertThat(componentDB.findByName("afafgadga")).isEqualTo(null);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> list = componentDB.findAll();

        assertThat(list.size()).isEqualTo(3);

        assertThat(list.get(0).getId()).isEqualTo(1);
        assertThat(list.get(1).getId()).isEqualTo(2);
        assertThat(list.get(2).getId()).isEqualTo(3);

        assertThat(list.get(0).getName()).isEqualTo("admin");
        assertThat(list.get(1).getName()).isEqualTo("user");
        assertThat(list.get(2).getName()).isEqualTo("userDelete");
    }

    @Test
    public void testAddUser() throws Exception {
        User user = componentDB.addUser("addUser");

        assertThat(user.getId()).isEqualTo(4);
        assertThat(user.getName()).isEqualTo("addUser");
        assertThat(user.getAccessLevel()).isEqualTo(AccessLevel.USER);
    }

    @Test
    public void testDeleteUser() throws Exception {
        assertThat(componentDB.findById(2).isDelete()).isFalse();
        componentDB.deleteUser(2);
        assertThat(componentDB.findById(2).isDelete()).isTrue();
    }

    @Test
    public void testRecoveryUser() throws Exception {
        assertThat(componentDB.findById(3).isDelete()).isTrue();
        componentDB.recoveryUser(3);
        assertThat(componentDB.findById(3).isDelete()).isFalse();
    }

    @Test
    public void testRenameUser() throws Exception {
        assertThat(componentDB.findById(2).getName()).isEqualTo("user");
        User user = componentDB.renameUser(2, "newName");
        assertThat(componentDB.findById(2).getId()).isEqualTo(user.getId());
        assertThat(componentDB.findById(2).getName()).isEqualTo(user.getName());
        assertThat(componentDB.findById(2).getAccessLevel()).isEqualTo(user.getAccessLevel());
    }
}