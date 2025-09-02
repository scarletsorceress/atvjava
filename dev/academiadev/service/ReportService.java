package dev.academiadev.service;

import dev.academiadev.model.*;
import dev.academiadev.repo.InMemoryDatabase;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private final InMemoryDatabase db;
    public ReportService(InMemoryDatabase db) { this.db = db; }

    public List<Course> coursesByDifficulty(Course.Difficulty d) {
        return db.coursesByTitle.values().stream()
                .filter(c -> c.getDifficultyLevel() == d)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public Set<String> activeInstructors() {
        return db.coursesByTitle.values().stream()
                .filter(c -> c.getStatus() == Course.Status.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Map<String, List<Student>> studentsGroupedByPlan() {
        return db.usersByEmail.values().stream()
                .filter(u -> !u.isAdmin())
                .map(u -> (Student) u)
                .collect(Collectors.groupingBy(s -> s.getSubscriptionPlan().getPlanName()));
    }

    public double averageProgressAllEnrollments() {
        return db.enrollments.stream()
                .mapToDouble(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> studentWithMostActiveEnrollments() {
        return db.enrollments.stream()
                .filter(Enrollment::isActive)
                .collect(Collectors.groupingBy(e -> e.getStudent(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
