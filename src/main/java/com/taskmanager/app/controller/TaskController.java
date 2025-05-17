package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
//import com.taskmanager.app.exception.MissingOrInvalidTokenException;
import com.taskmanager.app.dto.StatusUpdateRequest;
import com.taskmanager.app.dto.TaskResponse;
import com.taskmanager.app.model.*;
import com.taskmanager.app.service.TaskLogService;
import com.taskmanager.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskLogService taskLogService;

    @GetMapping("/tasks")
    public ApiResponse<List<TaskResponse>> getMyTasks(Authentication auth) {

        Long myId = (Long) auth.getDetails();
        List<Task> tasks = taskService.getTasksByUserId(myId);
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(Task task : tasks){
            List<TaskLog> taskLogs = taskLogService.getAllTaskLogByTaskId(task.getId(),myId);
            TaskResponse taskResponse = new TaskResponse(task, taskLogs);
            taskResponses.add(taskResponse);

        }

        return new ApiResponse<>(200, "Tasks retrieved successfully", taskResponses);
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
    @PatchMapping("/{taskId}/status")
    public ApiResponse<Task> updateStatus(
            @PathVariable Long taskId,
            @RequestBody StatusUpdateRequest body,
            Authentication auth
    ) {
        // parse the new status from JSON: { "status": "IN_PROGRESS" }

        Long userId    = (Long) auth.getDetails();

        Task updated = taskService.updateTaskStatus(taskId, body.getStatus(), userId);
        return new ApiResponse<>(200, "Status updated fam", updated);
    }
    @PatchMapping("/{taskId}/priority")
    public ApiResponse<Task> updatePriority(
            @PathVariable Long taskId,
            @RequestBody Map<String, String> body,
            Authentication auth
    ) {
        // Expect JSON: { "priority": "HIGH" }
        Priority newPriority = Priority.valueOf(body.get("priority"));
        Long userId = (Long) auth.getDetails();

        Task updated = taskService.updateTaskPriority(taskId, newPriority, userId);
        return new ApiResponse<>(200, "Priority updated!", updated);
    }
    @PostMapping("/tasks/extend-due-date")
    public String extendTaskDueDate(@RequestParam Long taskId) {
        taskService.extendTaskDueDate(taskId);
        return "Due date extended by 1 hour.";
    }
    @DeleteMapping("/{taskId}")
    public ApiResponse<String> deletetask(@PathVariable Long taskId, Authentication auth){
        Long userId = (Long) auth.getDetails();
        taskService.deleteTask(taskId,userId);
        return new ApiResponse<>(200, "success","deleted Task");
     }
}
