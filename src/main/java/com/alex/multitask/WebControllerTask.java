package com.alex.multitask;

import com.alex.multitask.tasks.*;
import com.alex.multitask.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by alex on 30.10.2016.
 */
@RestController
public class WebControllerTask {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task/all", method = RequestMethod.GET)
    public List<Task> getAllTasksNoText() {
        return taskService.getAllTasksNoText(taskRepository.findAll());
    }

    @RequestMapping(value = "/task/filter", method = RequestMethod.GET)
    public List<Task> getAllTasksByFilter(
            @RequestParam(value = "authorId", required = false, defaultValue = "0") int authorId,
            @RequestParam(value = "executorId", required = false, defaultValue = "0") int executorId,
            @RequestParam(value = "status", required = false, defaultValue = "noStatus") String status,
            @RequestParam(value = "deadline", required = false, defaultValue = "1970/01/01 03:00") String deadline) {
        Date dateDeadline;
        try {
            dateDeadline = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (!(status.equals("new") || status.equals("work") || status.equals("made") || status.equals("noStatus"))) {
            throw new BadRequestException();
        }
        return taskService.getAllTasksNoText(taskService.getAllTasksByFilter(authorId, executorId, status, dateDeadline));
    }

    @RequestMapping(value = "/task/byId", method = RequestMethod.GET)
    public TaskAndComment getTaskAndComment(@RequestParam(value = "taskId") int taskId) {
        return new TaskAndComment(taskRepository.findOne(taskId), commentRepository.findByTaskId(taskId));
    }

    @RequestMapping(value = "/task/new", method = RequestMethod.POST)
    public Task addNewTask(@RequestParam(value = "usedId") int usedId,
                           @RequestParam(value = "title") String title,
                           @RequestParam(value = "text") String text,
                           @RequestParam(value = "deadline") String deadline,
                           @RequestParam(value = "executorId") int executorId) {
        Date deadlineDate;
        try {
            deadlineDate = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (title.isEmpty()) {
            throw new BadRequestException();
        }
        if (text.isEmpty()) {
            throw new BadRequestException();
        }
        if (usersRepository.findOne(usedId) == null) {
            throw new NotFoundException();
        }
        if (usersRepository.findOne(executorId) == null) {
            throw new NotFoundException();
        }

        return taskRepository.save(new Task(usedId, title, text, deadlineDate, executorId));
    }

    @RequestMapping(value = "/task/edit", method = RequestMethod.POST)
    public Task editTask(@RequestParam(value = "usedId") int usedId,
                         @RequestParam(value = "taskId") int taskId,
                         @RequestParam(value = "title") String title,
                         @RequestParam(value = "text") String text,
                         @RequestParam(value = "deadline") String deadline,
                         @RequestParam(value = "executorId") int executorId,
                         @RequestParam(value = "status") String status) {
        Date deadlineDate;
        try {
            deadlineDate = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (title.isEmpty()) {
            throw new BadRequestException();
        }
        if (text.isEmpty()) {
            throw new BadRequestException();
        }
        if (usersRepository.findOne(usedId) == null) {
            throw new NotFoundException();
        }
        if (taskRepository.findOne(taskId) == null) {
            throw new NotFoundException();
        }
        if (usersRepository.findOne(executorId) == null) {
            throw new NotFoundException();
        }
        if (!(status.equals("work") || status.equals("made"))) {
            throw new BadRequestException();
        }

        return taskService.getEditTask(usedId, taskId, title, text, deadlineDate,
                executorId, taskService.statusTask(status));
    }

    @RequestMapping(value = "/comment/new", method = RequestMethod.POST)
    public Comment addComment(@RequestParam(value = "usedId") int usedId,
                              @RequestParam(value = "text") String text,
                              @RequestParam(value = "taskId") int taskId) {

        if (text.isEmpty()) {
            throw new BadRequestException();
        }
        if (usersRepository.findOne(usedId) == null) {
            throw new NotFoundException();
        }
        if (taskRepository.findOne(taskId) == null) {
            throw new NotFoundException();
        }
        if (commentRepository.findByTaskId(taskId) != null) {
            throw new ConflictException();
        }

        return commentRepository.save(new Comment(taskId, usedId, text));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public class ConflictException extends RuntimeException {
    }
}
