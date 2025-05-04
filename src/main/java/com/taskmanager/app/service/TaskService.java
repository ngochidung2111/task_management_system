package com.taskmanager.app.service;

import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.User;
import com.taskmanager.app.repository.TaskRepository;
import com.taskmanager.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    // Lấy tất cả công việc của người dùng theo userId
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task, Long userId) {
        // Find user by userId and set it for the task
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            task.setUser(user);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());

            // Save the task to the database
            return taskRepository.save(task);
        } else {
            // If user not found, return null (you can handle this more gracefully)
            return null;
        }
    }
}
