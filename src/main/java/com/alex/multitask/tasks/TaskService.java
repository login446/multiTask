package com.alex.multitask.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.alex.multitask.tasks.TaskSpecs.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by alex on 30.10.2016.
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksNoTextNoComments(List<Task> list) {
        if (list == null) {
            return null;
        }
        for (Task task : list) {
            task.setTaskText("");
            if (task.getComments() != null) {
                task.getComments().clear();
            }
        }
        return list;
    }

    public List<Task> getAllTasksByFilter(Integer authorId,
                                          Integer executorId,
                                          String status,
                                          Date deadline) {
        StatusTask statusTask = null;
        if (authorId == 0) {
            authorId = null;
        }
        if (executorId == 0) {
            executorId = null;
        }
        if (!status.equals("noStatus")) {
            statusTask = StatusTask.valueOf(status.toUpperCase());
        }
        if (deadline.getTime() == 0) {
            deadline = null;
        }

        return taskRepository.findAll(
                where(findByAuthorId(authorId))
                        .and(findByExecutorId(executorId))
                        .and(findByStatus(statusTask))
                        .and(findByDeadline(deadline)));
    }

    public Task getEditTask(int usedId, Task task, String title, String text, Date deadline,
                            int executorId, String status) {
        if (task == null) {
            return null;
        }
        task.setAuthorId(usedId);
        if (!title.equals("noTitle")) {
            task.setTaskTitle(title);
        }
        if (!text.equals("noText")) {
            task.setTaskText(text);
        }
        if (deadline.getTime() != 0) {
            task.setDeadline(deadline);
        }
        if (executorId != 0) {
            task.setExecutorId(executorId);
        }
        if (!status.equals("noStatus")) {
            task.setStatus(StatusTask.valueOf(status.toUpperCase()));
        }
        return taskRepository.save(task);
    }
}
