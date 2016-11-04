package com.alex.multitask.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by alex on 30.10.2016.
 */
@Service
public class TaskService {
    @Autowired
    private TaskComponentDB db;

    public List<Task> getAllTasksNoText(List<Task> list) {
        if(list == null)
            return null;

        for (Task task : list)
            task.setTaskText(null);
        return list;
    }

    public StatusTask statusTask(String status) {
        if (status.equals("new"))
            return StatusTask.NEW;
        if (status.equals("work"))
            return StatusTask.WORK;
        if (status.equals("made"))
            return StatusTask.MADE;
        return null;
    }

    public List<Task> getAllTasksByFilter(int authorId,
                                          int executorId,
                                          String status,
                                          Date deadline) {
        HashSet<Task> set = new HashSet<Task>();
        List<Task> result = new ArrayList<Task>();
        if (authorId != 0) {
            set.addAll(db.getAllTasksByAuthorId(authorId));
        }
        if (executorId != 0) {
            set.addAll(db.getAllTasksByExecutorId(executorId));
        }
        if (!status.equals("noStatus")) {
            set.addAll(db.getAllTasksByStatus(status.toUpperCase()));
        }
        if (deadline.getTime() != 0) {
            set.addAll(db.getAllTasksByDeadline(deadline));
        }
        result.addAll(set);
        return result;
    }
}
