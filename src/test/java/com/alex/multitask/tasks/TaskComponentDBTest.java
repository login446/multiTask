package com.alex.multitask.tasks;

import com.alex.multitask.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alex on 03.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/scriptTaskDBTest.sql")
public class TaskComponentDBTest {

    @Autowired
    private TaskComponentDB db;

    @Test
    public void testGetTask() throws Exception {
        Task task = db.getTask(1);
        assertThat(task.getTaskId()).isEqualTo(1);
        assertThat(task.getTaskId()).isEqualTo(1);
        assertThat(task.getTaskId()).isEqualTo(1);

        assertThat(db.getTask(99)).isNull();
    }

    @Test
    public void testGetComment() throws Exception {

    }

    @Test
    public void testGetAllTasks() throws Exception {

    }

    @Test
    public void testGetAllTasksByAuthorId() throws Exception {

    }

    @Test
    public void testGetAllTasksByExecutorId() throws Exception {

    }

    @Test
    public void testGetAllTasksByStatus() throws Exception {

    }

    @Test
    public void testGetAllTasksByDeadline() throws Exception {

    }

    @Test
    public void testAddNewTask() throws Exception {

    }

    @Test
    public void testAddNewTaskPlusStatus() throws Exception {

    }

    @Test
    public void testAddComment() throws Exception {

    }
}