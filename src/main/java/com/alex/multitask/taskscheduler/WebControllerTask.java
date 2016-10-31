package com.alex.multitask.taskscheduler;

import com.alex.multitask.UsersComponentDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void method2(@RequestParam(value = "usedId") String usedId,
                        @RequestParam(value = "authorId") String authorId,
                        @RequestParam(value = "executorId") String executorId,
                        @RequestParam(value = "status") String status,
                        @RequestParam(value = "deadline") String deadline) {
        int usedIdInt, authorIdInt, executorIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
            authorIdInt = Integer.parseInt(authorId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }


    }

    @RequestMapping(value = "/task/byId", method = RequestMethod.GET)
    public void method3(@RequestParam(value = "usedId") String usedId) {
        int usedIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }


    }

    @RequestMapping(value = "/task/new", method = RequestMethod.POST)
    public void method4(@RequestParam(value = "usedId") String usedId,
                        @RequestParam(value = "title") String title,
                        @RequestParam(value = "text") String text,
                        @RequestParam(value = "deadline") String deadline,
                        @RequestParam(value = "executorId") String executorId) {
        int usedIdInt, executorIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }


    }

    @RequestMapping(value = "/task/edit", method = RequestMethod.POST)
    public void method5(@RequestParam(value = "usedId") String usedId,
                        @RequestParam(value = "title") String title,
                        @RequestParam(value = "text") String text,
                        @RequestParam(value = "deadline") String deadline,
                        @RequestParam(value = "executorId") String executorId,
                        @RequestParam(value = "status") String status) {
        int usedIdInt, executorIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
            executorIdInt = Integer.parseInt(executorId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }


    }

    @RequestMapping(value = "/comment/new", method = RequestMethod.POST)
    public void method6(@RequestParam(value = "usedId") String usedId,
                        @RequestParam(value = "text") String text,
                        @RequestParam(value = "taskId") String taskId) {
        int usedIdInt, taskIdInt;
        try {
            usedIdInt = Integer.parseInt(usedId);
            taskIdInt = Integer.parseInt(taskId);
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }


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
