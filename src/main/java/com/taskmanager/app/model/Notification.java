package com.taskmanager.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Người nhận thông báo

    @Column(nullable = false, name = "message")
    private String message;  // Nội dung thông báo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private NotificationStatus status;  // Trạng thái thông báo (READ, UNREAD)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Thời gian tạo thông báo

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Thời gian cập nhật thông báo

    // Constructor
    public Notification() {}

    public Notification(User user, String message, NotificationStatus status) {
        this.user = user;
        this.message = message;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}