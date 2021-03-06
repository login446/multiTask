package com.alex.multitask.tasks;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by alex on 30.10.2016.
 */
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int taskId;
    @Column(name = "author_id", nullable = false, unique = false)
    private int authorId;
    @Column(name = "date_of_creation", nullable = false, unique = false)
    private Date dateOfCreation;
    @Column(name = "task_title", nullable = false, unique = false)
    private String taskTitle;
    @Column(name = "status", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private StatusTask status;
    @Column(name = "task_text", nullable = false, unique = false)
    private String taskText;
    private Date deadline;
    @Column(name = "executor_id", nullable = false, unique = false)
    private int executorId;
    @OneToMany(targetEntity = Comment.class)
    @JoinColumn(name = "task_id")
    private List<Comment> comments;

    public Task() {
    }

    public Task(int authorId, String taskTitle, String taskText, Date deadline, int executorId) {
        this(authorId, taskTitle, taskText, deadline, executorId, StatusTask.NEW);
    }

    public Task(int authorId, String taskTitle, String taskText, Date deadline, int executorId, StatusTask status) {
        this.authorId = authorId;
        this.dateOfCreation = new Date();
        this.taskTitle = taskTitle;
        this.status = status;
        this.taskText = taskText;
        this.deadline = deadline;
        this.executorId = executorId;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public StatusTask getStatus() {
        return status;
    }

    public String getTaskText() {
        return taskText;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List getComments() {
        return comments;
    }

    public void setComments(List comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return taskId == task.taskId;

    }

    @Override
    public int hashCode() {
        return taskId;
    }
}
