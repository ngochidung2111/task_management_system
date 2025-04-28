package com.taskmanager.app.service;

import com.taskmanager.app.model.Task;
import com.taskmanager.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    // Lấy tất cả công việc của người dùng theo userId
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}
