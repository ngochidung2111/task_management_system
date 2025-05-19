package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
import com.taskmanager.app.dto.TaskLogRequest;
import com.taskmanager.app.dto.TaskLogResponse;
import com.taskmanager.app.model.Status;
import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.TaskLog;
import com.taskmanager.app.model.User;
import com.taskmanager.app.service.TaskLogService;
import com.taskmanager.app.service.TaskService;
import com.taskmanager.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasklog")
public class TaskLogController {

    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private  TaskService taskService;  // Để fetch Task theo id
    @Autowired
    private  UserService userService;  // Để fetch User theo id
    @GetMapping("")
    public ApiResponse<List<TaskLog>> getAllTaskLog(){
        List<TaskLog> taskLogs = taskLogService.getAllTaskLog();
        return new ApiResponse<>(200, "success", taskLogs);
    }
    @GetMapping("/{taskId}")
    public ApiResponse<List<TaskLogResponse>> getTaskLogByTaskId(@PathVariable Long taskId, Authentication auth){
        Long userId = (Long) auth.getDetails();
        List<TaskLog> taskLogs = taskLogService.getAllTaskLogByTaskId(taskId,userId);
        List<TaskLogResponse> taskLogResponses = new ArrayList<>();

        for(TaskLog taskLog: taskLogs){
            TaskLogResponse taskLogResponse = new TaskLogResponse();
            taskLogResponse.setStatus(taskLog.getStatus());
            taskLogResponse.setComment(taskLog.getComment());
            taskLogResponse.setUpdateAt(taskLog.getUpdatedAt());
            taskLogResponse.setId(taskLog.getId());
            taskLogResponses.add(taskLogResponse);
        }
        return new ApiResponse<>(200, "success", taskLogResponses);
    }
    @PostMapping("/{taskId}")
    public ApiResponse<TaskLogResponse> createTaskLog(@PathVariable Long taskId, @RequestBody TaskLogRequest taskLogRequest, Authentication authentication) {
        Task task = taskService.getTaskById(taskId);
        User user = userService.getUserById((Long) authentication.getDetails());

        TaskLog taskLog = taskLogService.createTaskLog(task, taskLogRequest.getStatus(), taskLogRequest.getComment(), user);
        TaskLogResponse taskLogResponse = new TaskLogResponse();
        taskLogResponse.setId(taskLog.getId());
        taskLogResponse.setUpdateAt(taskLog.getUpdatedAt());
        taskLogResponse.setComment(taskLogRequest.getComment());
        taskLogResponse.setStatus(taskLogRequest.getStatus());
        return new ApiResponse<>(200, "success", taskLogResponse);
    }
    @DeleteMapping("/{taskLogId}")
    public ApiResponse<String> deleteTaskLog(@PathVariable Long taskLogId, Authentication authentication){
        Long userId = (Long) authentication.getDetails();
        taskLogService.deleteTaskLogById(taskLogId,userId);
        return new ApiResponse<>(200, "success", "delete task log id:" + taskLogId);
    }
}
