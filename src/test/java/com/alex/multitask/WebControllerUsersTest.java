package com.alex.multitask;

import com.alex.multitask.users.AccessLevel;
import com.alex.multitask.users.User;
import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.users.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class WebControllerUsersTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsersComponentDB db;

    @Mock
    private UsersService usersService;

    @Test
    public void testAddUser() throws Exception {
        when(db.findByName("newUser")).thenReturn(null);
        when(db.addUser("newUser")).thenReturn(new User("newUser"));

        mockMvc.perform(post("/users/add")
                .param("name", "newUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newUser"));
    }

    @Test
    public void testAddUserParamBad() throws Exception {
        when(db.findByName("")).thenReturn(null);
        when(db.addUser("")).thenReturn(new User(""));

        mockMvc.perform(post("/users/add")
                .param("name", ""))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/users/add"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddUserParamConflict() throws Exception {
        when(db.findByName("conflictUser")).thenReturn(new User("conflictUser"));
        when(db.addUser("conflictUser")).thenReturn(new User("conflictUser"));

        mockMvc.perform(post("/users/add")
                .param("name", "conflictUser"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testDeleteAdminUser() throws Exception {
        User user = new User("admin");
        user.setAccessLevel(AccessLevel.ADMIN);
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(new User("userDelete"));
        when(usersService.isUserAdmin(user)).thenReturn(true);
        verify(db).deleteUser(2);

        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User("user");
        when(db.findById(1)).thenReturn(user);
        when(usersService.isUserAdmin(user)).thenReturn(false);

        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserUser() throws Exception {
        User user = new User("user");
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(user);
        when(usersService.isUserAdmin(user)).thenReturn(false);

        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUserParamBad() throws Exception {
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "ff")
                .param("id", "sda"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUserNoUser() throws Exception {
        User user = new User("admin");
        user.setAccessLevel(AccessLevel.ADMIN);
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(null);
        when(usersService.isUserAdmin(user)).thenReturn(true);

        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserNoUsed() throws Exception {
        when(db.findById(1)).thenReturn(null);
        when(db.findById(2)).thenReturn(new User("user"));

        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecoveryAdminUser() throws Exception {
        User user = new User("admin");
        user.setAccessLevel(AccessLevel.ADMIN);
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(new User("userDelete"));
        when(usersService.isUserAdmin(user)).thenReturn(true);
        verify(db).recoveryUser(2);

        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRecoveryUser() throws Exception {
        User user = new User("user");
        when(db.findById(1)).thenReturn(user);
        when(usersService.isUserAdmin(user)).thenReturn(false);

        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRecoveryUserUser() throws Exception {
        User user = new User("user");
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(user);
        when(usersService.isUserAdmin(user)).thenReturn(false);

        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRecoveryUserParamBad() throws Exception {
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "ff")
                .param("id", "sda"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRecoveryUserNoUser() throws Exception {
        User user = new User("admin");
        user.setAccessLevel(AccessLevel.ADMIN);
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(null);
        when(usersService.isUserAdmin(user)).thenReturn(true);

        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecoveryUserNoUsed() throws Exception {
        when(db.findById(1)).thenReturn(null);
        when(db.findById(2)).thenReturn(new User("user"));

        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRenameUser() throws Exception {
        when(db.findById(1)).thenReturn(new User("user"));
        when(db.findByName("newName")).thenReturn(null);
        when(db.renameUser(1, "newName")).thenReturn(new User("newName"));

        mockMvc.perform(post("/users/rename")
                .param("usedId", "1")
                .param("id", "1")
                .param("name", "newName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newName"));
    }

    @Test
    public void testRenameAdminUser() throws Exception {
        User user = new User("admin");
        user.setAccessLevel(AccessLevel.ADMIN);
        when(db.findById(1)).thenReturn(user);
        when(db.findById(2)).thenReturn(new User("user"));
        when(db.findByName("newName")).thenReturn(null);
        when(usersService.isUserAdmin(user)).thenReturn(true);
        when(db.renameUser(2, "newName")).thenReturn(new User("newName"));

        mockMvc.perform(post("/users/rename")
                .param("usedId", "1")
                .param("id", "2")
                .param("name", "newName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newName"));
    }

    @Test
    public void testGetUsers() throws Exception {

    }
}