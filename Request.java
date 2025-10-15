package com.sara.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Request title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Resource type is required")
    @Enumerated(EnumType.STRING)
    private Resource.ResourceType resourceType;
    
    @NotNull(message = "Quantity needed is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantityNeeded;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Urgency level is required")
    @Enumerated(EnumType.STRING)
    private UrgencyLevel urgency;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.OPEN;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "needed_by")
    private LocalDateTime neededBy;
    
    public enum UrgencyLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    public enum RequestStatus {
        OPEN, IN_PROGRESS, FULFILLED, CANCELLED
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Request() {}
    
    public Request(String title, String description, Resource.ResourceType resourceType,
                  Integer quantityNeeded, String location, UrgencyLevel urgency, User user) {
        this.title = title;
        this.description = description;
        this.resourceType = resourceType;
        this.quantityNeeded = quantityNeeded;
        this.location = location;
        this.urgency = urgency;
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Resource.ResourceType getResourceType() { return resourceType; }
    public void setResourceType(Resource.ResourceType resourceType) { this.resourceType = resourceType; }
    
    public Integer getQuantityNeeded() { return quantityNeeded; }
    public void setQuantityNeeded(Integer quantityNeeded) { this.quantityNeeded = quantityNeeded; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }
    
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getNeededBy() { return neededBy; }
    public void setNeededBy(LocalDateTime neededBy) { this.neededBy = neededBy; }
    
    public String getUrgencyBadgeClass() {
        return switch (urgency) {
            case CRITICAL -> "bg-danger";
            case HIGH -> "bg-warning";
            case MEDIUM -> "bg-info";
            case LOW -> "bg-secondary";
        };
    }
}