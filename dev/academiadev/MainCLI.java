package dev.academiadev;

import dev.academiadev.repo.InMemoryDatabase;
import dev.academiadev.util.InitialData;
import dev.academiadev.util.GenericCsvExporter;
import dev.academiadev.model.*;
import dev.academiadev.service.*;
import dev.academiadev.exception.EnrollmentException;

import java.util.*;
import java.util.stream.Collectors;

public class MainCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private final InMemoryDatabase db = new InMemoryDatabase();
    private final EnrollmentService enrollmentService;
    private final SupportService supportService;
    private final ReportService reportService;

    private User loggedUser = null;

    public MainCLI(){
        InitialData.populate(db);
        enrollmentService = new EnrollmentService(db);
        supportService = new SupportService(db);
        reportService = new ReportService(db);
    }

    public static void main(String[] args) {
        new MainCLI().run();
    }

    private void run(){
        while (true) {
            System.out.println("--- AcademiaDev CLI ---");
            System.out.println("1) Login (email)");
            System.out.println("2) Listar cursos (ativos)");
            System.out.println("3) Abrir ticket de suporte");
            System.out.println("0) Sair");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": login(); break;
                    case "2": listActiveCourses(); break;
                    case "3": openTicket(); break;
                    case "0": System.out.println("até!"); return;
                    default: System.out.println("desconhecido");
                }
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }

    private void login(){
        System.out.print("email: ");
        String email = scanner.nextLine().trim().toLowerCase();
        User u = db.usersByEmail.get(email);
        if (u == null) {
            System.out.println("Usuario não encontrado");
            return;
        }
        loggedUser = u;
        System.out.println("Logado como: " + u);
        if (u.isAdmin()) adminMenu(); else studentMenu();
    }

    private void adminMenu(){
        while (true) {
            System.out.println("--- Admin Menu ---");
            System.out.println("1) Ativar/Desativar curso");
            System.out.println("2) Alterar plano de estudante");
            System.out.println("3) Processar ticket de suporte");
            System.out.println("4) Relatórios");
            System.out.println("5) Exportar CSV");
            System.out.println("9) Logout");
            System.out.print("> ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": toggleCourseStatus(); break;
                case "2": changeStudentPlan(); break;
                case "3": processTicket(); break;
                case "4": showReports(); break;
                case "5": exportCsvMenu(); break;
                case "9": loggedUser = null; return;
                default: System.out.println("desconhecido");
            }
        }
    }

    private void studentMenu(){
        while (true) {
            System.out.println("--- Student Menu ---");
            System.out.println("1) Matrícula em curso");
            System.out.println("2) Ver minhas matrículas");
            System.out.println("3) Atualizar progresso");
            System.out.println("4) Cancelar matrícula");
            System.out.println("9) Logout");
            System.out.print("> ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1": enrollInCourse(); break;
                case "2": viewMyEnrollments(); break;
                case "3": updateProgress(); break;
                case "4": cancelEnrollment(); break;
                case "9": loggedUser = null; return;
                default: System.out.println("desconhecido");
            }
        }
    }

    // --- operações do adm ---
    private void toggleCourseStatus(){
        System.out.print("Nome do curso: ");
        String title = scanner.nextLine();
        Course c = db.coursesByTitle.get(title);
        if (c==null){ System.out.println("não encontrado"); return; }
        c.setStatus(c.getStatus()==Course.Status.ACTIVE?Course.Status.INACTIVE:Course.Status.ACTIVE);
        System.out.println("Novo status: " + c.getStatus());
    }

    private void changeStudentPlan(){
        System.out.print("Email do estudante: "); String email = scanner.nextLine().trim().toLowerCase();
        User u = db.usersByEmail.get(email);
        if (u==null || u.isAdmin()){ System.out.println("Estudante não encontrado"); return; }
        Student s = (Student)u;
        System.out.println("Plano atual: " + s.getSubscriptionPlan().getPlanName());
        System.out.print("Novo plano (BASIC/PREMIUM): ");
        String np = scanner.nextLine().trim().toUpperCase();
        if (np.equals("BASIC")) s.setSubscriptionPlan(new dev.academiadev.model.BasicPlan());
        else s.setSubscriptionPlan(new dev.academiadev.model.PremiumPlan());
        System.out.println("Plano atualizado: " + s.getSubscriptionPlan().getPlanName());
    }

    private void processTicket(){
        SupportService ss = supportService;
        Optional<dev.academiadev.model.SupportTicket> ot = ss.processNextTicket(loggedUser);
        if (!ot.isPresent()) { System.out.println("No tickets"); return; }
        dev.academiadev.model.SupportTicket t = ot.get();
        System.out.println("Processando: " + t);
        System.out.println("Mensagem: " + t.getMessage());
        System.out.println("Pronto.");
    }

    private void showReports(){
        System.out.println("1) Cursos por dificuldade");
        System.out.println("2) Instrutores ativos");
        System.out.println("3) Estudantes por plano");
        System.out.println("4) Progresso médio de todos os estudantes");
        System.out.println("5) Estudante com mais matrículas ativas");
        System.out.print("> ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1":
                System.out.print("Dificuldade (BEGINNER/INTERMEDIATE/ADVANCED): ");
                try {
                    Course.Difficulty d = Course.Difficulty.valueOf(scanner.nextLine().trim().toUpperCase());
                    reportService.coursesByDifficulty(d).forEach(System.out::println);
                } catch (Exception ex){ System.out.println("invalid"); }
                break;
            case "2": reportService.activeInstructors().forEach(System.out::println); break;
            case "3":
                reportService.studentsGroupedByPlan().forEach((k,v)->{
                    System.out.println(k+":");
                    v.forEach(s->System.out.println("  " + s));
                });
                break;
            case "4": System.out.println("Average progress: " + reportService.averageProgressAllEnrollments()); break;
            case "5": System.out.println(reportService.studentWithMostActiveEnrollments().map(Object::toString).orElse("none")); break;
            default: System.out.println("unknown");
        }
    }

    private void exportCsvMenu(){
        System.out.println("Exportar qual lista: 1) Courses(cursos) 2) Students(estudantes) 3) Enrollments(matriculas)");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1":
                List<Course> courses = new ArrayList<>(db.coursesByTitle.values());
                System.out.println("Campos disponíveis: title,description,instructorName,durationInHours,difficultyLevel,status");
                System.out.print("Digite os campos separados por vírgula: ");
                List<String> fields = Arrays.stream(scanner.nextLine().split(",")).map(String::trim).collect(Collectors.toList());
                System.out.println(GenericCsvExporter.exportToCsv(courses, fields));
                break;
            case "2":
                List<User> users = new ArrayList<>(db.usersByEmail.values());
                System.out.println("Campos disponíveis: name,email (for Student: subscriptionPlan)");
                System.out.print("Digite os campos separados por vírgula: ");
                List<String> f2 = Arrays.stream(scanner.nextLine().split(",")).map(String::trim).collect(Collectors.toList());
                System.out.println(GenericCsvExporter.exportToCsv(users, f2));
                break;
            case "3":
                List<Enrollment> ens = db.enrollments;
                System.out.println("Campos disnponíveis: id,student,course,progress,active (nota: course e student são objetos)");
                System.out.print("Digite os campos separados por vírgula: ");
                List<String> f3 = Arrays.stream(scanner.nextLine().split(",")).map(String::trim).collect(Collectors.toList());
                System.out.println(GenericCsvExporter.exportToCsv(ens, f3));
                break;
            default: System.out.println("unknown");
        }
    }

    // --- operações do estudantr ---
    private void enrollInCourse(){
        Student s = (Student) loggedUser;
        System.out.print("Course title: "); String title = scanner.nextLine();
        Course c = db.coursesByTitle.get(title);
        if (c==null) { System.out.println("not found"); return; }
        try {
            Enrollment e = enrollmentService.enroll(s, c);
            System.out.println("Enrolled: " + e);
        } catch (EnrollmentException ex) {
            System.out.println("Could not enroll: " + ex.getMessage());
        }
    }

    private void viewMyEnrollments(){
        Student s = (Student) loggedUser;
        db.enrollments.stream().filter(e->e.getStudent().getEmail().equals(s.getEmail())).forEach(System.out::println);
    }

    private void updateProgress(){
        Student s = (Student) loggedUser;
        System.out.print("Course title: "); String title = scanner.nextLine();
        Course c = db.coursesByTitle.get(title);
        if (c==null) { System.out.println("not found"); return; }
        System.out.print("New progress (0-100): ");
        try {
            double p = Double.parseDouble(scanner.nextLine());
            enrollmentService.updateProgress(s, c, p);
            System.out.println("Updated");
        } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    private void cancelEnrollment(){
        Student s = (Student) loggedUser;
        System.out.print("Course title: "); String title = scanner.nextLine();
        Course c = db.coursesByTitle.get(title);
        if (c==null) { System.out.println("not found"); return; }
        try {
            enrollmentService.cancelEnrollment(s, c);
            System.out.println("Canceled");
        } catch (EnrollmentException ex) { System.out.println("Error: " + ex.getMessage()); }
    }

    // --- operações gerais de todos ---
    private void listActiveCourses(){
        db.coursesByTitle.values().stream().filter(c->c.getStatus()==Course.Status.ACTIVE).forEach(System.out::println);
    }

    private void openTicket(){
        System.out.print("Your email: "); String email = scanner.nextLine().trim().toLowerCase();
        User u = db.usersByEmail.get(email);
        if (u==null) { System.out.println("user not found"); return; }
        System.out.print("Title: "); String t = scanner.nextLine();
        System.out.print("Message: "); String m = scanner.nextLine();
        supportService.openTicket(u, t, m);
        System.out.println("Ticket opened");
    }
}
