package com.alex.multitask.taskscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alex on 30.10.2016.
 */
@Component
public class TaskComponentDB {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Task getTask(int taskId) {
        return taskRepository.findOne(taskId);
    }

    public Comment getComment(int taskId) {
        List<Comment> list = commentRepository.findByTaskId(taskId);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    public List<Task> getAllTasks() {
        Iterable<Task> list = taskRepository.findAll();
        ArrayList<Task> result = new ArrayList<Task>();
        for (Task task : list)
            result.add(task);
        return result;
    }

    public List<Task> getAllTasksByAuthorId(int authorId) {
        return taskRepository.findByAuthorId(authorId);
    }

    public List<Task> getAllTasksByExecutorId(int executorId) {
        return taskRepository.findByExecutorId(executorId);
    }

    public List<Task> getAllTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getAllTasksByDeadline(Date deadline) {
        return taskRepository.findByDeadline(deadline);
    }

    public Task addNewTask(int usedId,
                           String title,
                           String text,
                           Date deadline,
                           int executorId) {
        return taskRepository.save(new Task(usedId, title, text, deadline, executorId));
    }

    public Task addNewTask(int usedId,
                           String title,
                           String text,
                           Date deadline,
                           int executorId,
                           String status) {
        StatusTask statusTask = null;
        if (status.equals("new"))
            statusTask = StatusTask.NEW;
        if (status.equals("work"))
            statusTask = StatusTask.WORK;
        if (status.equals("made"))
            statusTask = StatusTask.MADE;

        return taskRepository.save(new Task(usedId, title, text, deadline, executorId, statusTask));
    }

    public Comment addComment(int taskId, int authorId, String commentText) {
        return commentRepository.save(new Comment(taskId, authorId, commentText));
    }
}
