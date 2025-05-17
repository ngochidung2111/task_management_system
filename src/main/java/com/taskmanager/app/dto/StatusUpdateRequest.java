package com.taskmanager.app.dto;

import com.taskmanager.app.model.Status;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
