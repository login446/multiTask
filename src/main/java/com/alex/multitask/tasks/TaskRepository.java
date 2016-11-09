package com.alex.multitask.tasks;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alex on 29.10.2016.
 */
public interface TaskRepository extends CrudRepository<Task, Integer>, JpaSpecificationExecutor {
    List<Task> findAll();
}
