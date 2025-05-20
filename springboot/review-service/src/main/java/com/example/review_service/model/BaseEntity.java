package com.example.review_service.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass 
public class BaseEntity {

    @Getter 
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter 
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Getter
    @Setter 
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // @PrePersist: Được gọi khi entity lần đầu tiên được lưu
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // @PreUpdate: Được gọi khi entity được cập nhật
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // @PreRemove: Được gọi khi entity bị xóa
    @PreRemove
    public void preRemove() {
        this.deletedAt = LocalDateTime.now();
    }

    
}
