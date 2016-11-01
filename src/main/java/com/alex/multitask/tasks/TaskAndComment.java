package com.alex.multitask.tasks;

/**
 * Created by admin on 31.10.2016.
 */
public class TaskAndComment {
    private Task task;
    private Comment comment;

    public TaskAndComment() {
    }

    public TaskAndComment(Task task, Comment comment) {
        this.task = task;
        this.comment = comment;
    }

    public Task getTask() {
        return task;
    }

    public Comment getComment() {
        return comment;
    }
}
