package com.taskmanager.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;  // Tiêu đề công việc
    @Column(name = "description")
    private String description;  // Mô tả công việc

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "status")
    private Status status;  // Trạng thái công việc (PENDING, IN_PROGRESS, COMPLETED)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "priority")
    private Priority priority;  // Mức độ ưu tiên (LOW, MEDIUM, HIGH)
    @Column(name = "due_date")
    private LocalDateTime dueDate;  // Ngày hết hạn công việc

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    @JsonBackReference
    private User user;  // Người được phân công công việc (liên kết tới bảng `users`)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Thời gian tạo công việc

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Thời gian cập nhật công việc
    @Column(name = "notified", nullable = false)
    private boolean notified = false;  // Trạng thái đã gửi thông báo chưa

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    // Constructor
    public Task() {}

    public Task(String title, String description, Status status, Priority priority, LocalDateTime dueDate, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
