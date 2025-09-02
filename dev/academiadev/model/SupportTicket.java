package dev.academiadev.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportTicket {
    private final String id = UUID.randomUUID().toString();
    private final String title;
    private final String message;
    private final User requester;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public SupportTicket(User requester, String title, String message) {
        this.requester = requester;
        this.title = title;
        this.message = message;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public User getRequester() { return requester; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override public String toString(){ return String.format("[%s] %s by %s", id, title, requester.getEmail()); }
}
