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
}
