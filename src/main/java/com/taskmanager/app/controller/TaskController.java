package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
//import com.taskmanager.app.exception.MissingOrInvalidTokenException;
import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.User;
import com.taskmanager.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("")
    public ApiResponse<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return new ApiResponse<>(200,"success",tasks);
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        if (!tasks.isEmpty()) {
            return new ApiResponse<>(200, "Tasks retrieved successfully", tasks);
        } else {
            return new ApiResponse<>(200, "No tasks found for the user", null);
        }
    }
    @GetMapping("/tasks")
    public ApiResponse<List<Task>> getMyTasks(Authentication auth) {

        Long myId = (Long) auth.getDetails();
        List<Task> tasks = taskService.getTasksByUserId(myId);

        return new ApiResponse<>(200, "Tasks retrieved successfully", tasks);
    }
    @PostMapping("/task")
    public ResponseEntity<Object> createTask(@RequestBody Task task, Authentication authentication) {

        Long userId = (Long) authentication.getDetails();  // Get userId from JWT

        Map<String,Object> body = new HashMap<>();
        body.put("status", "200");
        body.put("message", "Profile retrieved");
        body.put("data", task);
        // Call the service to save the task
        Task createdTask = taskService.createTask(task, userId);

        if (createdTask != null) {
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tạo entity, vui lòng kiểm tra lại dữ liệu.");
        }
    }
}
