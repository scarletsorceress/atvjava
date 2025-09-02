package dev.academiadev.model;

import java.util.Objects;

public class Course {
    public enum Difficulty { BEGINNER, INTERMEDIATE, ADVANCED }
    public enum Status { ACTIVE, INACTIVE }

    private final String title; 
    private String description;
    private String instructorName;
    private double durationInHours;
    private Difficulty difficultyLevel;
    private Status status;

    public Course(String title, String description, String instructorName, double durationInHours, Difficulty difficultyLevel, Status status) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public double getDurationInHours() { return durationInHours; }
    public void setDurationInHours(double durationInHours) { this.durationInHours = durationInHours; }
    public Difficulty getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(Difficulty difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return title.equals(course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s (%.1fh) by %s", title, difficultyLevel, status, durationInHours, instructorName);
    }
}
