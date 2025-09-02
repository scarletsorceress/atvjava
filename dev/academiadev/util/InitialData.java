package dev.academiadev.util;

import dev.academiadev.model.*;
import dev.academiadev.repo.InMemoryDatabase;

public class InitialData {
    public static void populate(InMemoryDatabase db) {
        Course c1 = new Course("Java 101", "Introdução ao Java", "Ana Silva", 12.0, Course.Difficulty.BEGINNER, Course.Status.ACTIVE);
        Course c2 = new Course("Data Structures", "Estruturas de Dados em Java", "Carlos Souza", 20.0, Course.Difficulty.INTERMEDIATE, Course.Status.ACTIVE);
        Course c3 = new Course("Advanced Algorithms", "Algoritmos Avançados", "Dr. Algo", 30.0, Course.Difficulty.ADVANCED, Course.Status.INACTIVE);
        Course c4 = new Course("Web Dev Basics", "HTML/CSS/JS", "Ana Silva", 8.0, Course.Difficulty.BEGINNER, Course.Status.ACTIVE);

        db.coursesByTitle.put(c1.getTitle(), c1);
        db.coursesByTitle.put(c2.getTitle(), c2);
        db.coursesByTitle.put(c3.getTitle(), c3);
        db.coursesByTitle.put(c4.getTitle(), c4);

        Admin admin = new Admin("Admin One", "admin@academiadev.com");
        Student s1 = new Student("João", "joao@example.com", new BasicPlan());
        Student s2 = new Student("Maria", "maria@example.com", new PremiumPlan());
        Student s3 = new Student("Pedro", "pedro@example.com", new BasicPlan());

        db.usersByEmail.put(admin.getEmail(), admin);
        db.usersByEmail.put(s1.getEmail(), s1);
        db.usersByEmail.put(s2.getEmail(), s2);
        db.usersByEmail.put(s3.getEmail(), s3);

        db.enrollments.add(new Enrollment(s1, c1));
        db.enrollments.add(new Enrollment(s1, c4));
        Enrollment e = new Enrollment(s2, c2); e.setProgress(45.0); db.enrollments.add(e);
        // nota: c3 está inativo e nao deveria aceitar enrollments.

        db.ticketQueue.add(new SupportTicket(s1, "Problema com curso", "Não estou vendo o conteúdo do módulo 2."));
        db.ticketQueue.add(new SupportTicket(s2, "Pagamento", "Como alterar meu método de pagamento?"));
    }
}
