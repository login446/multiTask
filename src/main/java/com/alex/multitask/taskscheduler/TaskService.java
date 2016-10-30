package com.alex.multitask.taskscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alex on 30.10.2016.
 */
@Service
public class TaskService {
    @Autowired
    private ComponentDB db;

    public List<Task> getAllTasksNoText(List<Task> list){
        for (Task task : list)
            task.setTaskText(null);
        return list;
    }
}
