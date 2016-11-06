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
    private TaskRepository taskRepository;

    public List<Task> getAllTasksNoText(List<Task> list) {
        if (list == null) {
            return null;
        }
        for (Task task : list) {
            task.setTaskText("");
        }
        return list;
    }

    public StatusTask statusTask(String status) {
        if (status.equals("new")) {
            return StatusTask.NEW;
        }
        if (status.equals("work")) {
            return StatusTask.WORK;
        }
        if (status.equals("made")) {
            return StatusTask.MADE;
        }
        return null;
    }

    public List<Task> getAllTasksByFilter(int authorId,
                                          int executorId,
                                          String status,
                                          Date deadline) {
        HashSet<Task> set = new HashSet<Task>();
        List<Task> result = new ArrayList<Task>();
        if (authorId != 0) {
            set.addAll(taskRepository.findByAuthorId(authorId));
        }
        if (executorId != 0) {
            set.addAll(taskRepository.findByExecutorId(executorId));
        }
        if (!status.equals("noStatus")) {
            set.addAll(taskRepository.findByStatus(statusTask(status)));
        }
        if (deadline.getTime() != 0) {
            set.addAll(taskRepository.findByDeadline(deadline));
        }
        result.addAll(set);
        return result;
    }

    public Task getEditTask(int usedId, int taskId, String title, String text, Date deadline,
                            int executorId, StatusTask status) {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            return null;
        }
        task.setAuthorId(usedId);
        task.setTaskTitle(title);
        task.setTaskText(text);
        task.setDeadline(deadline);
        task.setExecutorId(executorId);
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
