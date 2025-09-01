package academiadev.util;
import academiadev.model.*;

import java.util.*;

public class InitialData {
    public final Map<String, Course> coursesByTitle = new HashMap<>();
    public final Map<String, User> usersByEmail = new HashMap<>();
    public final List<Enrollment> enrollments = new ArrayList<>();
    public final Queue<SupportTicket> ticketQueue = new ArrayDeque<>();

    public InitialData() {
        loadCourses();
        loadUsers();
        loadEnrollments();
        loadTickets();
    }

    private void loadCourses() {
        addCourse(new Course("Java 8 Fundamentos", "Fundamentos do Java 8", "Carlos Silva", 20, Difficulty.BEGINNER, Status.ACTIVE));
        addCourse(new Course("Spring Boot Avançado", "Microservices com Spring Boot", "Ana Pereira", 30, Difficulty.ADVANCED, Status.ACTIVE));
        addCourse(new Course("Algoritmos e Estruturas de Dados", "Teoria e prática", "Rafael Costa", 40, Difficulty.INTERMEDIATE, Status.ACTIVE));
        addCourse(new Course("Kotlin para Android", "Kotlin básico e intermediário", "Daniela Lima", 25, Difficulty.INTERMEDIATE, Status.INACTIVE));
    }

    private void addCourse(Course c) {
        coursesByTitle.put(c.getTitle(), c);
    }

    private void loadUsers() {
        Admin admin = new Admin("Admin Master", "admin@academiadev.com");
        usersByEmail.put(admin.getEmail(), admin);

        Student s1 = new Student("João", "joao@dominio.com", new BasicPlan());
        Student s2 = new Student("Maria", "maria@dominio.com", new PremiumPlan());
        Student s3 = new Student("Pedro", "pedro@dominio.com", new BasicPlan());

        usersByEmail.put(s1.getEmail(), s1);
        usersByEmail.put(s2.getEmail(), s2);
        usersByEmail.put(s3.getEmail(), s3);
    }

    private void loadEnrollments() {
        Student joao = (Student) usersByEmail.get("joao@dominio.com");
        Student maria = (Student) usersByEmail.get("maria@dominio.com");

        enrollments.add(new Enrollment(joao, coursesByTitle.get("Java 8 Fundamentos")));
        enrollments.add(new Enrollment(maria, coursesByTitle.get("Java 8 Fundamentos")));
        Enrollment e = new Enrollment(maria, coursesByTitle.get("Spring Boot Avançado"));
        e.setProgress(40.0);
        enrollments.add(e);
    }

    private void loadTickets() {
        ticketQueue.add(new SupportTicket("joao@dominio.com", "Problema com vídeo", "O vídeo 3 não carrega"));
    }
}
