package academiadev.service;
import academiadev.exception.EnrollmentException;
import academiadev.model.*;
import academiadev.model.Course.Difficulty;
import academiadev.model.Course.Status;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlatformService {
    private final Map<String, Course> coursesByTitle;
    private final Map<String, User> usersByEmail;
    private final List<Enrollment> enrollments;
    private final Queue<SupportTicket> ticketQueue;

    public PlatformService(Map<String, Course> coursesByTitle, Map<String, User> usersByEmail,
                           List<Enrollment> enrollments, Queue<SupportTicket> ticketQueue) {
        this.coursesByTitle = coursesByTitle;
        this.usersByEmail = usersByEmail;
        this.enrollments = enrollments;
        this.ticketQueue = ticketQueue;
    }

    // AUTH
    public Optional<User> loginByEmail(String email) {
        if (email == null) return Optional.empty();
        return Optional.ofNullable(usersByEmail.get(email.toLowerCase()));
    }

    // Courses
    public List<Course> listActiveCourses() {
        return coursesByTitle.values().stream()
                .filter(c -> c.getStatus() == Status.ACTIVE)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public void setCourseStatus(String title, Status status) {
        Course c = coursesByTitle.get(title);
        if (c != null) c.setStatus(status);
    }

    // Enrollment operations
    public void enrollStudent(String studentEmail, String courseTitle) throws EnrollmentException {
        User u = usersByEmail.get(studentEmail.toLowerCase());
        if (!(u instanceof Student)) throw new EnrollmentException("Usuário não é estudante ou não existe.");
        Student s = (Student) u;
        Course c = coursesByTitle.get(courseTitle);
        if (c == null) throw new EnrollmentException("Curso não existe.");
        if (c.getStatus() != Status.ACTIVE) throw new EnrollmentException("Curso não está ativo.");
        // check already enrolled active
        boolean already = enrollments.stream()
                .anyMatch(en -> en.getStudent().getEmail().equals(s.getEmail())
                        && en.getCourse().getTitle().equals(courseTitle)
                        && en.isActive());
        if (already) throw new EnrollmentException("Já matriculado neste curso.");

        // count active enrollments
        long activeCount = enrollments.stream()
                .filter(Enrollment::isActive)
                .filter(en -> en.getStudent().getEmail().equals(s.getEmail()))
                .count();

        if (!s.getSubscriptionPlan().canEnroll((int) activeCount)) {
            throw new EnrollmentException("Plano de assinatura não permite nova matrícula.");
        }

        Enrollment e = new Enrollment(s, c);
        enrollments.add(e);
    }

    public List<Enrollment> getEnrollmentsForStudent(String studentEmail) {
        return enrollments.stream()
                .filter(en -> en.getStudent().getEmail().equals(studentEmail.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void updateProgress(String studentEmail, String courseTitle, double progress) throws EnrollmentException {
        Enrollment e = enrollments.stream()
                .filter(en -> en.isActive()
                        && en.getStudent().getEmail().equals(studentEmail.toLowerCase())
                        && en.getCourse().getTitle().equals(courseTitle))
                .findFirst()
                .orElse(null);
        if (e == null) throw new EnrollmentException("Matrícula ativa não encontrada.");
        e.setProgress(progress);
    }

    public void cancelEnrollment(String studentEmail, String courseTitle) throws EnrollmentException {
        Enrollment e = enrollments.stream()
                .filter(en -> en.isActive()
                        && en.getStudent().getEmail().equals(studentEmail.toLowerCase())
                        && en.getCourse().getTitle().equals(courseTitle))
                .findFirst()
                .orElse(null);
        if (e == null) throw new EnrollmentException("Matrícula ativa não encontrada.");
        e.cancel();
    }

    // Tickets
    public void openTicket(String creatorEmail, String title, String message) {
        ticketQueue.add(new SupportTicket(creatorEmail, title, message));
    }

    public Optional<SupportTicket> processNextTicket(User admin) {
        if (!(admin instanceof academiadev.model.Admin)) {
            return Optional.empty();
        }
        SupportTicket t = ticketQueue.poll();
        return Optional.ofNullable(t);
    }

    // Reports (using Stream API)
    public List<Course> coursesByDifficulty(Difficulty difficulty) {
        return coursesByTitle.values().stream()
                .filter(c -> c.getDifficultyLevel() == difficulty)
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public Set<String> uniqueActiveInstructors() {
        return coursesByTitle.values().stream()
                .filter(c -> c.getStatus() == Status.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Map<String, List<Student>> groupStudentsByPlan() {
        return usersByEmail.values().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.groupingBy(s -> s.getSubscriptionPlan().getName()));
    }

    public double averageProgressOverall() {
        return enrollments.stream()
                .mapToDouble(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> studentWithMostActiveEnrollments() {
        return enrollments.stream()
                .filter(Enrollment::isActive)
                .collect(Collectors.groupingBy(en -> en.getStudent(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Utilities to access internal structures (for CSV exports etc.)
    public List<Course> allCourses() { return new ArrayList<>(coursesByTitle.values()); }
    public List<Student> allStudents() {
        return usersByEmail.values().stream()
                .filter(u -> u instanceof Student).map(u -> (Student) u).collect(Collectors.toList());
    }
    public List<User> allUsers() { return new ArrayList<>(usersByEmail.values()); }
    public List<Enrollment> allEnrollments() { return new ArrayList<>(enrollments); }
    public List<SupportTicket> allTickets() { return new ArrayList<>(ticketQueue); }
}
