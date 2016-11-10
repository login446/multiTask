package com.alex.multitask.tasks;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by alex on 09.11.2016.
 */
public class TaskSpecs {
    public static Specification<Task> findByAuthorId(final int authorId) {
        return new Specification<Task>() {
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (authorId == 0) {
                    return null;
                }
                return builder.equal(root.get("authorId"), authorId);
            }
        };
    }

    public static Specification<Task> findByExecutorId(final int executorId) {
        return new Specification<Task>() {
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (executorId == 0) {
                    return null;
                }
                return builder.equal(root.get("executorId"), executorId);
            }
        };
    }

    public static Specification<Task> findByStatus(final StatusTask status) {
        return new Specification<Task>() {
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (status == null) {
                    return null;
                }
                return builder.equal(root.get("status"), status);
            }
        };
    }

    public static Specification<Task> findByDeadline(final Date deadline) {
        return new Specification<Task>() {
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (deadline == null || deadline.getTime() == 0) {
                    return null;
                }
                return builder.equal(root.get("deadline"), deadline);
            }
        };
    }
}
