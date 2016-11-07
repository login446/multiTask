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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 04.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/scriptTaskServiceTest.sql")
public class TaskServiceTest {
    @Autowired
    TaskService service;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void getAllTasksNoTextTest() throws Exception {
        List<Task> list = new ArrayList<Task>();
        list.add(new Task(1, "title", "text", new Date(), 1));
        list.add(new Task(1, "title", "text", new Date(), 1));
        list.add(new Task(1, "title", "text", new Date(), 1));
        List<Task> result = service.getAllTasksNoText(list);

        assertEquals(result.get(0).getTaskText(), "");
        assertEquals(result.get(1).getTaskText(), "");
        assertEquals(result.get(2).getTaskText(), "");
    }

    @Test
    public void getAllTasksByFilterTest() throws Exception {
        Date dateDefault = new Date("1970/01/01 03:00");
        List<Task> listAuthorId = service.getAllTasksByFilter(1, 0, "noStatus", dateDefault);
        List<Task> listExecutorId = service.getAllTasksByFilter(0, 2, "noStatus", dateDefault);
        List<Task> listStatus = service.getAllTasksByFilter(0, 0, "new", dateDefault);
        List<Task> listDeadline = service.getAllTasksByFilter(0, 0, "noStatus", new Date("2016/11/07 17:23"));
        List<Task> listAuthorIdAndStatus = service.getAllTasksByFilter(1, 0, "new", dateDefault);
        List<Task> listNoFilter = service.getAllTasksByFilter(0, 0, "noStatus", dateDefault);

        assertThat(listAuthorId.size()).isEqualTo(2);
        assertThat(listAuthorId.get(0).getAuthorId()).isEqualTo(1);
        assertThat(listAuthorId.get(1).getAuthorId()).isEqualTo(1);

        assertThat(listExecutorId.size()).isEqualTo(2);
        assertThat(listExecutorId.get(0).getExecutorId()).isEqualTo(2);
        assertThat(listExecutorId.get(1).getExecutorId()).isEqualTo(2);

        assertThat(listStatus.size()).isEqualTo(2);
        assertThat(listStatus.get(0).getStatus()).isSameAs(StatusTask.NEW);
        assertThat(listStatus.get(1).getStatus()).isSameAs(StatusTask.NEW);

        assertThat(listDeadline.size()).isEqualTo(2);
        assertThat(listDeadline.get(0).getDeadline().getTime()).isEqualTo(new Date("2016/11/07 17:23").getTime());
        assertThat(listDeadline.get(1).getDeadline().getTime()).isEqualTo(new Date("2016/11/07 17:23").getTime());

        assertThat(listAuthorIdAndStatus.size()).isEqualTo(2);
        assertThat(listAuthorId.get(0).getAuthorId()).isEqualTo(1);
        assertThat(listAuthorId.get(1).getAuthorId()).isEqualTo(1);
        assertThat(listAuthorId.get(0).getStatus()).isEqualTo(StatusTask.NEW);
        assertThat(listAuthorId.get(1).getStatus()).isEqualTo(StatusTask.NEW);

        assertThat(listNoFilter.size()).isEqualTo(0);
    }

    @Test
    public void getEditTaskTest() throws Exception {
        Task task = taskRepository.findOne(1);

        assertThat(task.getAuthorId()).isEqualTo(1);
        assertThat(task.getTaskId()).isEqualTo(1);
        assertThat(task.getTaskTitle()).isEqualTo("title1");

        Task editTask = service.getEditTask(1, taskRepository.findOne(1), "newTitle", "newText",
                new Date("2022/11/07 17:23"), 1, StatusTask.WORK);

        assertThat(editTask.getAuthorId()).isEqualTo(1);
        assertThat(editTask.getTaskId()).isEqualTo(1);
        assertThat(editTask.getTaskTitle()).isEqualTo("newTitle");

        assertThat(service.getEditTask(1, taskRepository.findOne(99), "newTitle", "newText",
                new Date("2022/11/07 17:23"), 1, StatusTask.WORK)).isNull();
    }
}