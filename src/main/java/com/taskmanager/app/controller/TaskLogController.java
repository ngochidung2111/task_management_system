package com.taskmanager.app.controller;

import com.taskmanager.app.Response.ApiResponse;
import com.taskmanager.app.model.TaskLog;
import com.taskmanager.app.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasklog")
public class TaskLogController {

    @Autowired
    private TaskLogService taskLogService;

    @GetMapping("")
    public ApiResponse<List<TaskLog>> getAllTaskLog(){
        List<TaskLog> taskLogs = taskLogService.getAllTaskLog();
        return new ApiResponse<>("success", "success", taskLogs);
    }
}
