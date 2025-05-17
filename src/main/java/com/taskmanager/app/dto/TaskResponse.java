package com.taskmanager.app.dto;

import com.taskmanager.app.model.Task;
import com.taskmanager.app.model.TaskLog;

import java.util.ArrayList;
import java.util.List;

public class TaskResponse {
    private Task task;
    private List<TaskLogResponse> taskLogResponses;

    public TaskResponse(Task task, List<TaskLog> taskLog){
        this.task = task;
        if(taskLog == null ){
            this.taskLogResponses =  List.of();
        }

        else {
            this.taskLogResponses = new ArrayList<>();
            for (TaskLog taskLog1 : taskLog) {
                TaskLogResponse taskLogResponse = new TaskLogResponse();
                taskLogResponse.setStatus(taskLog1.getStatus());
                taskLogResponse.setComment(taskLog1.getComment());
                taskLogResponse.setUpdateAt(taskLog1.getUpdatedAt());
                taskLogResponse.setId(taskLog1.getId());
                this.taskLogResponses.add(taskLogResponse);
            }
        }
    }
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<TaskLogResponse> getTaskLogs() {
        return taskLogResponses;
    }

    public void setTaskLogs(List<TaskLogResponse> taskLogs) {
        this.taskLogResponses = taskLogs;
    }
}
