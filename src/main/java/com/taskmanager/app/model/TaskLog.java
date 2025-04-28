package com.taskmanager.app.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "task_logs")
public class TaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;  // Công việc mà log này ghi lại

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private Status status;  // Trạng thái mới của công việc

    @Column(name = "comment")
    private String comment;  // Bình luận khi thay đổi trạng thái

    @ManyToOne
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;  // Người cập nhật trạng thái công việc

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Thời gian cập nhật trạng thái

    // Constructor
    public TaskLog() {}

    public TaskLog(Task task, Status status, String comment, User updatedBy) {
        this.task = task;
        this.status = status;
        this.comment = comment;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
}