package com.taskmanager.app.service;

import com.taskmanager.app.model.TaskLog;
import com.taskmanager.app.repository.TaskLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskLogService {
    @Autowired
    private TaskLogRepository taskLogRepository;

    public List<TaskLog> getAllTaskLog(){
        return taskLogRepository.findAll();
    }

}
