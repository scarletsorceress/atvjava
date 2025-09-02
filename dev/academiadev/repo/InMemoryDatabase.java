package dev.academiadev.repo;

import dev.academiadev.model.*;
import java.util.*;

public class InMemoryDatabase {

    public final Map<String, Course> coursesByTitle = new HashMap<>();
    public final Map<String, User> usersByEmail = new HashMap<>();

    public final List<Enrollment> enrollments = new ArrayList<>();

    public final Queue<SupportTicket> ticketQueue = new ArrayDeque<>();

    public InMemoryDatabase() {}
}
