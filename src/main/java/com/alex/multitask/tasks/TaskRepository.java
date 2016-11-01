package com.alex.multitask.tasks;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByAuthorId(int authorId);
    List<Task> findByExecutorId(int executorId);
    List<Task> findByStatus(String status);
    List<Task> findByDeadline(Date deadline);
}
