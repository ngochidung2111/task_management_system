package com.taskmanager.app.repository;

import com.taskmanager.app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByDueDateBefore(LocalDateTime dueDate);
    List<Task> findByDueDateBeforeAndNotifiedFalse(LocalDateTime dueDate);
}
