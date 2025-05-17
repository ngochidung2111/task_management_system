package com.taskmanager.app.service;

import com.taskmanager.app.model.*;
import com.taskmanager.app.repository.TaskLogRepository;
import com.taskmanager.app.repository.TaskRepository;
import com.taskmanager.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskLogRepository taskLogRepository;
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    // Lấy tất cả công việc của người dùng theo userId
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
    public Task createTask(Task task, Long userId) {
        // Find user by userId and set it for the task
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            task.setUser(user);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            System.out.println("DUEDATE"+task.getDueDate());
            // Save the task to the database
            return taskRepository.save(task);
        } else {
            // If user not found, return null (you can handle this more gracefully)
            return null;
        }
    }
    public Task updateTaskStatus(Long taskId, Status newStatus, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // optional: check owner so nobody updates someone else's tasks
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Cant hack me bruh");
        }

        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
    @Transactional
    public void deleteTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("not your task");
        }
        taskLogRepository.deleteByTaskId(taskId);
        taskRepository.deleteById(taskId);
    }
    public Task updateTaskPriority(Long taskId, Priority newPriority, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // optional flex: make sure only owner can tweak it
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Not your task bruh");
        }

        task.setPriority(newPriority);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
    public List<Task> getTasksDueSoon() {
        LocalDateTime oneHourLater = LocalDateTime.now().plusHours(1);  // Thời gian hiện tại cộng 1 giờ
        return taskRepository.findByDueDateBeforeAndNotifiedFalse(oneHourLater);  // Tìm tất cả công việc có thời gian đến hạn trước 1 giờ
    }
    // Cập nhật trạng thái công việc đã gửi thông báo
    public void updateTaskNotified(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setNotified(true);  // Đánh dấu đã gửi thông báo
        taskRepository.save(task);  // Lưu lại thay đổi
    }
    public Task extendTaskDueDate(Long taskId) {
        // Retrieve the task by its ID
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        // Add 1 hour to the current due date
        task.setDueDate(task.getDueDate().plusHours(1));

        // Save the updated task back to the database
        return taskRepository.save(task);
    }
}
