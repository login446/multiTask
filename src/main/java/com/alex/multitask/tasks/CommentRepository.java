package com.alex.multitask.tasks;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by alex on 29.10.2016.
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findByTaskId(int taskId);
}
