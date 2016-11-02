package com.alex.multitask;

import com.alex.multitask.users.User;
import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.users.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testRecoveryUser() throws Exception {

    }

    @Test
    public void testRenameUser() throws Exception {

    }

    @Test
    public void testGetUsers() throws Exception {

    }
}