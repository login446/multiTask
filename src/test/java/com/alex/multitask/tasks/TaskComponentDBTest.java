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

import java.util.Date;
import java.util.List;

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
        assertThat(task.getAuthorId()).isEqualTo(1);
        assertThat(task.getExecutorId()).isEqualTo(1);

        assertThat(db.getTask(99)).isNull();
    }

    @Test
    public void testGetComment() throws Exception {
        Comment comment = db.getComment(2);
        assertThat(comment.getAuthorId()).isEqualTo(1);
        assertThat(comment.getCommentText()).isEqualTo("comment1");

        assertThat(db.getComment(99)).isNull();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> list = db.getAllTasks();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getTaskTitle()).isEqualTo("title1");
        assertThat(list.get(1).getTaskTitle()).isEqualTo("title2");
        assertThat(list.get(2).getTaskTitle()).isEqualTo("title3");
    }

    @Test
    public void testGetAllTasksByAuthorId() throws Exception {
        List<Task> list = db.getAllTasksByAuthorId(1);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getAuthorId()).isEqualTo(1);
        assertThat(list.get(1).getAuthorId()).isEqualTo(1);

        List<Task> listEmpty = db.getAllTasksByAuthorId(99);
        assertThat(listEmpty.size()).isEqualTo(0);
    }

    @Test
    public void testGetAllTasksByExecutorId() throws Exception {
        List<Task> list = db.getAllTasksByExecutorId(2);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getExecutorId()).isEqualTo(2);
        assertThat(list.get(1).getExecutorId()).isEqualTo(2);

        List<Task> listEmpty = db.getAllTasksByAuthorId(99);
        assertThat(listEmpty.size()).isEqualTo(0);
    }

    @Test
    public void testGetAllTasksByStatus() throws Exception {
        List<Task> list = db.getAllTasksByStatus(StatusTask.NEW);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getStatus()).isSameAs(StatusTask.NEW);
        assertThat(list.get(1).getStatus()).isSameAs(StatusTask.NEW);

        List<Task> listEmpty = db.getAllTasksByStatus(null);
        assertThat(listEmpty.size()).isEqualTo(0);
    }

    @Test
    public void testGetAllTasksByDeadline() throws Exception {
        Date date = new Date("2016/11/07 17:23");
        List<Task> list = db.getAllTasksByDeadline(date);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getDeadline().getTime()).isEqualTo(date.getTime());
        assertThat(list.get(1).getDeadline().getTime()).isEqualTo(date.getTime());

        List<Task> listEmpty = db.getAllTasksByDeadline(new Date("2002/11/11"));
        assertThat(listEmpty.size()).isEqualTo(0);
    }

    @Test
    public void testAddNewTask() throws Exception {
        Task task = new Task(2, "title33", "text33", new Date(), 2, StatusTask.NEW);
        Task result = db.addNewTask(task);

        assertThat(result.getAuthorId()).isEqualTo(task.getAuthorId());
        assertThat(result.getTaskTitle()).isEqualTo(task.getTaskTitle());
        assertThat(result.getTaskText()).isEqualTo(task.getTaskText());
        assertThat(result.getDeadline()).isEqualTo(task.getDeadline());
        assertThat(result.getExecutorId()).isEqualTo(task.getExecutorId());
        assertThat(result.getStatus()).isEqualTo(task.getStatus());
    }

    @Test
    public void testAddComment() throws Exception {
        Comment comment = new Comment(1, 1, "text");
        Comment result = db.addComment(comment);

        assertThat(result.getAuthorId()).isEqualTo(comment.getAuthorId());
        assertThat(result.getTaskId()).isEqualTo(comment.getTaskId());
        assertThat(result.getCommentText()).isEqualTo(comment.getCommentText());
        assertThat(result.getDateOfCreation()).isEqualTo(comment.getDateOfCreation());
    }
}