package dev.academiadev.service;

import dev.academiadev.model.*;
import dev.academiadev.repo.InMemoryDatabase;

import java.util.Optional;

public class SupportService {
    private final InMemoryDatabase db;
    public SupportService(InMemoryDatabase db) { this.db = db; }

    public void openTicket(User user, String title, String message) {
        db.ticketQueue.add(new SupportTicket(user, title, message));
    }

    public Optional<SupportTicket> processNextTicket(User caller) {
        if (!caller.isAdmin()) throw new IllegalArgumentException("Apenas admins podem processar tickets");
        return Optional.ofNullable(db.ticketQueue.poll());
    }
}
