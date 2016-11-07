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
            set.addAll(taskRepository.findByStatus(StatusTask.valueOf(status.toUpperCase())));
        }
        if (deadline.getTime() != 0) {
            set.addAll(taskRepository.findByDeadline(deadline));
        }

        for (Task task : set) {
            if (authorId != 0 && task.getAuthorId() != authorId) {
                continue;
            }
            if (executorId != 0 && task.getExecutorId() != executorId) {
                continue;
            }
            if (!status.equals("noStatus") && task.getStatus() != StatusTask.valueOf(status.toUpperCase())) {
                continue;
            }
            if (deadline.getTime() != 0 && task.getDeadline().getTime() != deadline.getTime()) {
                continue;
            }
            result.add(task);
        }

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
