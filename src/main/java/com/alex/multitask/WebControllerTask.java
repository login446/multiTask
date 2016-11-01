package com.alex.multitask;

import com.alex.multitask.users.UsersComponentDB;
import com.alex.multitask.tasks.*;
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
    private TaskComponentDB taskDB;

    @Autowired
    private UsersComponentDB usersDB;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task/all", method = RequestMethod.GET)
    public List<Task> getAllTasksNoText() {
        return taskService.getAllTasksNoText(taskDB.getAllTasks());
    }

    @RequestMapping(value = "/task/filter", method = RequestMethod.GET)
    public List<Task> getAllTasksByFilter(
            @RequestParam(value = "authorId", required = false, defaultValue = "0") String authorId,
            @RequestParam(value = "executorId", required = false, defaultValue = "0") String executorId,
            @RequestParam(value = "status", required = false, defaultValue = "noStatus") String status,
            @RequestParam(value = "deadline", required = false, defaultValue = "1970/01/01 03:00") String deadline) {
        int authorIdInt, executorIdInt;
        Date dateDeadline;
        try {
            authorIdInt = Integer.parseInt(authorId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
        try {
            dateDeadline = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (!(status.equals("new") || status.equals("work") || status.equals("made") || status.equals("noStatus")))
            throw new BadRequestException();

        return taskService.getAllTasksNoText(taskService.getAllTasksByFilter(authorIdInt, executorIdInt, status, dateDeadline));
    }

    @RequestMapping(value = "/task/byId", method = RequestMethod.GET)
    public TaskAndComment getTaskAndComment(@RequestParam(value = "taskId") String taskId) {
        int taskIdInt;
        try {
            taskIdInt = Integer.parseInt(taskId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }

        return new TaskAndComment(taskDB.getTask(taskIdInt), taskDB.getComment(taskIdInt));
    }

    @RequestMapping(value = "/task/new", method = RequestMethod.POST)
    public Task addNewTask(@RequestParam(value = "usedId") String usedId,
                           @RequestParam(value = "title") String title,
                           @RequestParam(value = "text") String text,
                           @RequestParam(value = "deadline") String deadline,
                           @RequestParam(value = "executorId") String executorId) {
        int usedIdInt, executorIdInt;
        Date deadlineDate;
        try {
            usedIdInt = Integer.parseInt(usedId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
        try {
            deadlineDate = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (title.isEmpty())
            throw new BadRequestException();
        if (text.isEmpty())
            throw new BadRequestException();
        if (usersDB.findById(usedIdInt) == null)
            throw new NotFoundException();
        if (usersDB.findById(executorIdInt) == null)
            throw new NotFoundException();

        return taskDB.addNewTask(usedIdInt, title, text, deadlineDate, executorIdInt);
    }

    @RequestMapping(value = "/task/edit", method = RequestMethod.POST)
    public Task addNewTaskStatus(@RequestParam(value = "usedId") String usedId,
                                 @RequestParam(value = "title") String title,
                                 @RequestParam(value = "text") String text,
                                 @RequestParam(value = "deadline") String deadline,
                                 @RequestParam(value = "executorId") String executorId,
                                 @RequestParam(value = "status") String status) {
        int usedIdInt, executorIdInt;
        Date deadlineDate;
        try {
            usedIdInt = Integer.parseInt(usedId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
        try {
            deadlineDate = new Date(deadline);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }
        if (title.isEmpty())
            throw new BadRequestException();
        if (text.isEmpty())
            throw new BadRequestException();
        if (usersDB.findById(usedIdInt) == null)
            throw new NotFoundException();
        if (usersDB.findById(executorIdInt) == null)
            throw new NotFoundException();
        if (!(status.equals("new") || status.equals("work") || status.equals("made")))
            throw new BadRequestException();

        return taskDB.addNewTask(usedIdInt, title, text, deadlineDate, executorIdInt, status);
    }

    @RequestMapping(value = "/comment/new", method = RequestMethod.POST)
    public Comment addComment(@RequestParam(value = "usedId") String usedId,
                              @RequestParam(value = "text") String text,
                              @RequestParam(value = "taskId") String taskId) {
        int usedIdInt, taskIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
            taskIdInt = Integer.parseInt(taskId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
        if (text.isEmpty())
            throw new BadRequestException();
        if (usersDB.findById(usedIdInt) == null)
            throw new NotFoundException();
        if (taskDB.getTask(taskIdInt) == null)
            throw new NotFoundException();
        if (taskDB.getComment(taskIdInt) != null)
            throw new ConflictException();

        return taskDB.addComment(taskIdInt, usedIdInt, text);
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