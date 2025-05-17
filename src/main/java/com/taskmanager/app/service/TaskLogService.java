package com.taskmanager.app.service;

import com.taskmanager.app.model.Status;
import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.TaskLog;
import com.taskmanager.app.model.User;
import com.taskmanager.app.repository.TaskLogRepository;
import com.taskmanager.app.repository.TaskRepository;
import com.taskmanager.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskLogService {
    @Autowired
    private TaskLogRepository taskLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskLog> getAllTaskLog(){
        return taskLogRepository.findAll();
    }
    public List<TaskLog> getAllTaskLogByTaskId(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("not your task");
        }
        return taskLogRepository.findByTaskId(taskId);
    }
    public TaskLog createTaskLog(Task task, Status status, String comment, User updatedBy) {


        TaskLog taskLog = new TaskLog(task, status, comment, updatedBy);
        if(updatedBy != null){
            taskLog.setUpdatedAt(LocalDateTime.now());
        }
        return taskLogRepository.save(taskLog);
    }
}
