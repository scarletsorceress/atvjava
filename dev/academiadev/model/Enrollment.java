package dev.academiadev.model;

import java.util.UUID;

public class Enrollment {
    private final String id = UUID.randomUUID().toString();
    private final Student student;
    private final Course course;
    private double progress;
    private boolean active;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.progress = 0.0;
        this.active = true;
    }

    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getProgress() { return progress; }
    public void setProgress(double progress) {
        if (progress < 0 || progress > 100) throw new IllegalArgumentException("Progresso deve estar entre 0 e 100");
        this.progress = progress;
    }
    public boolean isActive() { return active; }
    public void cancel() { this.active = false; }

    @Override
    public String toString() {
        return String.format("Matrícula[%s -> %s] Progresso=%.1f%% Ativo=%s", student.getEmail(), course.getTitle(), progress, active);
    }
}
