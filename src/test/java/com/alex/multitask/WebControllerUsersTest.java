package com.alex.multitask;

import com.alex.multitask.users.AccessLevel;
import com.alex.multitask.users.User;
import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.users.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

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

    @MockBean
    private UsersComponentDB db;

    @MockBean
    private UsersService usersService;

    @Test
    public void testAddUser() throws Exception {
        when(db.findByName("newUser")).thenReturn(null);
        when(db.addUser("newUser")).thenReturn(new User(1, "newUser", new Date(), AccessLevel.USER, false));
        mockMvc.perform(post("/users/add")
                .param("name", "nnn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
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