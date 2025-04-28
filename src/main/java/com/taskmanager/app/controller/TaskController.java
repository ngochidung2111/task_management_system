package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
import com.taskmanager.app.model.Task;
import com.taskmanager.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ApiResponse<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return new ApiResponse<>("success","success",tasks);
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        if (!tasks.isEmpty()) {
            return new ApiResponse<>("success", "Tasks retrieved successfully", tasks);
        } else {
            return new ApiResponse<>("error", "No tasks found for the user", null);
        }
    }
}
