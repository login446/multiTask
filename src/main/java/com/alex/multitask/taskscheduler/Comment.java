package com.alex.multitask.taskscheduler;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alex on 30.10.2016.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;
    @Column(name = "task_id", nullable = false, unique = false)
    private int taskId;
    @Column(name = "date_of_creation", nullable = false, unique = false)
    private Date dateOfCreation;
    @Column(name = "author_id", nullable = false, unique = false)
    private int authorId;
    @Column(name = "comment_text", nullable = false, unique = false)
    private String commentText;

    public Comment() {
    }

    public Comment(int taskId, int authorId, String commentText) {
        this.taskId = taskId;
        this.dateOfCreation = new Date();
        this.authorId = authorId;
        this.commentText = commentText;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getTaskId() {
        return taskId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getCommentText() {
        return commentText;
    }
}
