package com.alex.multitask.taskscheduler;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by alex on 29.10.2016.
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {
}
