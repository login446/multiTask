package com.alex.multitask.taskscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 30.10.2016.
 */
@Component
public class ComponentDB {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        Iterable<Task> list = taskRepository.findAll();
        ArrayList<Task> result = new ArrayList<Task>();
        for (Task task : list)
            result.add(task);
        return result;
    }
}
