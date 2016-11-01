package com.alex.multitask;

import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.users.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

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