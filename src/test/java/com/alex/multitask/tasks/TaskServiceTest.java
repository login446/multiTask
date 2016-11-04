package com.alex.multitask.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by admin on 04.11.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {
    @InjectMocks
    TaskService service;

    @Mock
    TaskComponentDB db;

    @Test
    public void getAllTasksNoText() throws Exception {
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
    public void statusTask() throws Exception {
        assertEquals(service.statusTask("new"), StatusTask.NEW);
        assertEquals(service.statusTask("work"), StatusTask.WORK);
        assertEquals(service.statusTask("made"), StatusTask.MADE);
        assertNull(service.statusTask("nagggme"));
    }
}