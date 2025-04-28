package com.taskmanager.app.repository;

import com.taskmanager.app.model.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {

}
