package dev.academiadev.service;

import dev.academiadev.model.*;
import dev.academiadev.repo.InMemoryDatabase;
import dev.academiadev.exception.EnrollmentException;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final InMemoryDatabase db;

    public EnrollmentService(InMemoryDatabase db) { this.db = db; }

    public Enrollment enroll(Student student, Course course) {
        if (course.getStatus() != Course.Status.ACTIVE) throw new EnrollmentException("Course is not ACTIVE");

        boolean already = db.enrollments.stream()
                .anyMatch(e -> e.getStudent().getEmail().equals(student.getEmail()) && e.getCourse().equals(course) && e.isActive());
        if (already) throw new EnrollmentException("Estudante já está matriculado neste curso");

        long activeCount = db.enrollments.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()) && e.isActive())
                .count();
        if (!student.getSubscriptionPlan().canEnrollMore((int) activeCount)) {
            throw new EnrollmentException("Plano de assinatura não permite mais matrículas ativas");
        }

        Enrollment e = new Enrollment(student, course);
        db.enrollments.add(e);
        return e;
    }

    public void updateProgress(Student student, Course course, double progress) {
        Enrollment e = findActiveEnrollment(student, course).orElseThrow(() -> new EnrollmentException("Matrícula ativa não encontrada"));
        e.setProgress(progress);
    }

    public Optional<Enrollment> findActiveEnrollment(Student s, Course c) {
        return db.enrollments.stream()
                .filter(en -> en.getStudent().getEmail().equals(s.getEmail()) && en.getCourse().equals(c) && en.isActive())
                .findFirst();
    }

    public void cancelEnrollment(Student student, Course course) {
        Enrollment e = findActiveEnrollment(student, course).orElseThrow(() -> new EnrollmentException("Matrícula ativa não encontrada"));
        e.cancel();
    }
}
