package com.taskmanager.app.dto;

import com.taskmanager.app.model.Status;

public class TaskLogRequest {
    private Status status;
    private String comment;

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public Status getStatus() {
        return status;
    }
}
