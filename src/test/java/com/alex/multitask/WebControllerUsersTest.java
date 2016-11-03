package com.alex.multitask;

import com.alex.multitask.users.UsersComponentDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Sql("/scriptUsersTest.sql")
public class WebControllerUsersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersComponentDB db;

    @Test
    public void testAddUser() throws Exception {
        mockMvc.perform(post("/users/add")
                .param("name", "freeUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("freeUser"))
                .andExpect(jsonPath("$.delete").value("false"));
    }

    @Test
    public void testAddUserParamBad() throws Exception {
        mockMvc.perform(post("/users/add")
                .param("name", ""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/users/add"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddUserParamConflict() throws Exception {
        mockMvc.perform(post("/users/add")
                .param("name", "user"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testDeleteAdminUser() throws Exception {
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "2"))
                .andExpect(status().isOk());

        assertTrue(db.findById(2).isDelete());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "2")
                .param("id", "2"))
                .andExpect(status().isOk());

        assertTrue(db.findById(2).isDelete());
    }

    @Test
    public void testDeleteUserUser() throws Exception {
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "2")
                .param("id", "1"))
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
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "1")
                .param("id", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserNoUsed() throws Exception {
        mockMvc.perform(delete("/users/byId")
                .param("usedId", "999")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecoveryAdminUser() throws Exception {
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "3"))
                .andExpect(status().isOk());

        assertFalse(db.findById(3).isDelete());
    }

    @Test
    public void testRecoveryUser() throws Exception {
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "3")
                .param("id", "3"))
                .andExpect(status().isOk());

        assertFalse(db.findById(3).isDelete());
    }

    @Test
    public void testRecoveryUserUser() throws Exception {
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "2")
                .param("id", "1"))
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
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "1")
                .param("id", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecoveryUserNoUsed() throws Exception {
        mockMvc.perform(post("/users/restore/byId")
                .param("usedId", "999")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRenameUser() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "2")
                .param("id", "2")
                .param("name", "freeName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("freeName"));
    }

    @Test
    public void testRenameAdminUser() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "1")
                .param("id", "2")
                .param("name", "freeName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("freeName"));
    }

    @Test
    public void testRenameUserBadParam() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "df")
                .param("id", "fdf")
                .param("name", "freeName"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRenameUserNameIsEmpty() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "1")
                .param("id", "2")
                .param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRenameUserNoUsed() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "555")
                .param("id", "2")
                .param("name", "freeName"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRenameUserNoUser() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "1")
                .param("id", "555")
                .param("name", "freeName"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRenameUserConflictName() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "2")
                .param("id", "2")
                .param("name", "admin"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testRenameUserAdmin() throws Exception {
        mockMvc.perform(post("/users/rename")
                .param("usedId", "2")
                .param("id", "1")
                .param("name", "freeName"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users")
                .param("usedId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("admin"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("user"));
    }

    @Test
    public void testGetUsersAdmin() throws Exception {
        mockMvc.perform(get("/users")
                .param("usedId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("admin"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("user"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("userDelete"));
    }

    @Test
    public void testGetUsersBadParam() throws Exception {
        mockMvc.perform(get("/users")
                .param("usedId", "dfd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUsersNoUsed() throws Exception {
        mockMvc.perform(get("/users")
                .param("usedId", "99"))
                .andExpect(status().isNotFound());
    }
}