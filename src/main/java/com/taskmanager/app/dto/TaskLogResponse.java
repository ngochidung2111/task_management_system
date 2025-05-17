package com.taskmanager.app.dto;

import com.taskmanager.app.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskLogResponse {
    private Long id;
    private Status status;
    private String comment;
    private LocalDateTime updateAt;

    public Status getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
