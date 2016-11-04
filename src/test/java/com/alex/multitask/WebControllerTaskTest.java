package com.alex.multitask;

import com.alex.multitask.tasks.TaskComponentDB;
import com.alex.multitask.tasks.TaskService;
import com.alex.multitask.users.UsersComponentDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Sql("/scriptTest.sql")
public class WebControllerTaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersComponentDB usersDB;

    @Autowired
    private TaskComponentDB taskDB;

    @Autowired
    private TaskService taskService;

    @Test
    public void testGetAllTasksNoText() throws Exception {
        mockMvc.perform(get("/task/all"))
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$[0].getTaskText").value(null))
                .andExpect(jsonPath("$[1].getTaskText").value(null))
                .andExpect(jsonPath("$[2].getTaskText").value(null));
    }

    @Test
    public void testGetAllTasksByFilter() throws Exception {

    }

    @Test
    public void testGetTaskAndComment() throws Exception {

    }

    @Test
    public void testAddNewTask() throws Exception {

    }

    @Test
    public void testAddNewTaskStatus() throws Exception {

    }

    @Test
    public void testAddComment() throws Exception {

    }
}