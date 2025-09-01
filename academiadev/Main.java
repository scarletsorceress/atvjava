package academiadev;
import academiadev.model.*;
import academiadev.service.PlatformService;
import academiadev.service.EnrollmentException;
import academiadev.util.GenericCsvExporter;
import academiadev.util.InitialData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private final PlatformService service;
    private User currentUser;

    public Main(PlatformService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        InitialData initial = new InitialData();
        PlatformService service = new PlatformService(
                initial.coursesByTitle, initial.usersByEmail, initial.enrollments, initial.ticketQueue);
        new Main(service).run();
    }

    private void run() {
        System.out.println("=== AcademiaDev - Protótipo ===");
        while (true) {
            if (currentUser == null) {
                System.out.print("Digite seu e-mail para login (ou 'exit'): ");
                String email = scanner.nextLine().trim();
                if ("exit".equalsIgnoreCase(email)) break;
                Optional<User> u = service.loginByEmail(email);
                if (u.isPresent()) {
                    currentUser = u.get();
                    System.out.println("Logado como: " + currentUser);
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            } else {
                showMenuFor(currentUser);
            }
        }
        System.out.println("Saindo... até mais!");
    }

    private void showMenuFor(User u) {
        System.out.println("\n--- Menu (" + u.getClass().getSimpleName() + ") ---");
        if (u instanceof academiadev.model.Admin) {
            System.out.println("1) Ativar/Inativar curso");
            System.out.println("2) Alterar plano de aluno");
            System.out.println("3) Atender próximo ticket");
            System.out.println("4) Relatórios");
            System.out.println("5) Exportar dados para CSV");
        } else {
            System.out.println("1) Listar cursos ativos");
            System.out.println("2) Matricular-se em curso");
            System.out.println("3) Consultar minhas matrículas");
            System.out.println("4) Atualizar progresso");
            System.out.println("5) Cancelar matrícula");
            System.out.println("6) Abrir ticket de suporte");
        }
        System.out.println("0) Logout");
        System.out.print("Escolha: ");
        String opt = scanner.nextLine().trim();
        try {
            if (u instanceof academiadev.model.Admin) handleAdminOption(opt);
            else handleStudentOption(opt);
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void handleAdminOption(String opt) {
        switch (opt) {
            case "1":
                listAllCourses();
                System.out.print("Título do curso para alterar status: ");
                String t = scanner.nextLine();
                Course c = service.allCourses().stream().filter(cc -> cc.getTitle().equals(t)).findFirst().orElse(null);
                if (c == null) { System.out.println("Curso não encontrado."); break; }
                System.out.println("Status atual: " + c.getStatus());
                System.out.print("Novo status (ACTIVE/INACTIVE): ");
                String ns = scanner.nextLine();
                if ("ACTIVE".equalsIgnoreCase(ns)) service.setCourseStatus(t, Course.Status.ACTIVE);
                else service.setCourseStatus(t, Course.Status.INACTIVE);
                System.out.println("Status alterado.");
                break;
            case "2":
                System.out.print("Email do aluno: ");
                String em = scanner.nextLine();
                User u = service.loginByEmail(em).orElse(null);
                if (!(u instanceof Student)) { System.out.println("Aluno não encontrado."); break; }
                Student s = (Student) u;
                System.out.println("Plano atual: " + s.getSubscriptionPlan().getName());
                System.out.print("Novo plano (BasicPlan/PremiumPlan): ");
                String np = scanner.nextLine();
                if ("BasicPlan".equalsIgnoreCase(np)) s.setSubscriptionPlan(new academiadev.model.BasicPlan());
                else s.setSubscriptionPlan(new academiadev.model.PremiumPlan());
                System.out.println("Plano alterado.");
                break;
            case "3":
                Optional<SupportTicket> tkt = service.processNextTicket(currentUser);
                if (tkt.isPresent()) {
                    System.out.println("Atendido: " + tkt.get());
                } else {
                    System.out.println("Nenhum ticket na fila ou usuário não tem permissão.");
                }
                break;
            case "4":
                showReports();
                break;
            case "5":
                exportCsvInteractive();
                break;
            case "0":
                logout();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void handleStudentOption(String opt) throws EnrollmentException {
        Student s = (Student) currentUser;
        switch (opt) {
            case "1":
                listActiveCourses();
                break;
            case "2":
                listActiveCourses();
                System.out.print("Título do curso para matricular: ");
                String title = scanner.nextLine();
                service.enrollStudent(s.getEmail(), title);
                System.out.println("Matriculado com sucesso.");
                break;
            case "3":
                List<Enrollment> ens = service.getEnrollmentsForStudent(s.getEmail());
                System.out.println("Suas matrículas:");
                ens.forEach(System.out::println);
                break;
            case "4":
                System.out.print("Título do curso que quer atualizar progresso: ");
                String t = scanner.nextLine();
                System.out.print("Novo progresso (0-100): ");
                double p = Double.parseDouble(scanner.nextLine());
                service.updateProgress(s.getEmail(), t, p);
                System.out.println("Progresso atualizado.");
                break;
            case "5":
                System.out.print("Título do curso para cancelar matrícula: ");
                String ct = scanner.nextLine();
                service.cancelEnrollment(s.getEmail(), ct);
                System.out.println("Matrícula cancelada.");
                break;
            case "6":
                System.out.print("Título do ticket: ");
                String ttl = scanner.nextLine();
                System.out.print("Mensagem: ");
                String msg = scanner.nextLine();
                service.openTicket(s.getEmail(), ttl, msg);
                System.out.println("Ticket aberto.");
                break;
            case "0":
                logout();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void showReports() {
        System.out.println("\n--- Relatórios ---");
        System.out.println("1) Cursos por nível");
        System.out.println("2) Instrutores únicos de cursos ativos");
        System.out.println("3) Alunos agrupados por plano");
        System.out.println("4) Média geral de progresso");
        System.out.println("5) Aluno com maior número de matrículas ativas");
        System.out.print("Escolha: ");
        String op = scanner.nextLine().trim();
        switch (op) {
            case "1":
                System.out.print("Nível (BEGINNER/INTERMEDIATE/ADVANCED): ");
                String lv = scanner.nextLine().trim();
                Course.Difficulty d = Course.Difficulty.valueOf(lv.toUpperCase());
                List<Course> list = service.coursesByDifficulty(d);
                list.forEach(System.out::println);
                break;
            case "2":
                service.uniqueActiveInstructors().forEach(System.out::println);
                break;
            case "3":
                service.groupStudentsByPlan().forEach((k,v)-> {
                    System.out.println(k + ": " + v.stream().map(Student::getEmail).collect(Collectors.joining(", ")));
                });
                break;
            case "4":
                System.out.printf("Média geral de progresso: %.2f%%\n", service.averageProgressOverall());
                break;
            case "5":
                Optional<Student> top = service.studentWithMostActiveEnrollments();
                System.out.println("Aluno topo: " + top.map(Student::getEmail).orElse("Nenhum"));
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void exportCsvInteractive() {
        System.out.println("Exportar qual entidade? (courses/students/enrollments)");
        String ent = scanner.nextLine().trim().toLowerCase();
        switch (ent) {
            case "courses":
                List<Course> cs = service.allCourses();
                System.out.println("Campos disponíveis: title,description,instructorName,durationInHours,difficultyLevel,status");
                System.out.print("Campos separados por vírgula: ");
                List<String> fields = Arrays.stream(scanner.nextLine().split(","))
                        .map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList());
                String csv = GenericCsvExporter.exportToCsv(cs, fields);
                System.out.println("\n--- CSV ---\n" + csv);
                break;
            case "students":
                List<Student> ss = service.allStudents();
                System.out.println("Campos disponíveis: name,email,subscriptionPlan");
                System.out.print("Campos separados por vírgula: ");
                List<String> sfields = Arrays.stream(scanner.nextLine().split(","))
                        .map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList());
                String csv2 = GenericCsvExporter.exportToCsv(ss, sfields);
                System.out.println("\n--- CSV ---\n" + csv2);
                break;
            case "enrollments":
                List<Enrollment> es = service.allEnrollments();
                System.out.println("Campos disponíveis: student,course,progress,active");
                System.out.print("Campos separados por vírgula: ");
                List<String> efields = Arrays.stream(scanner.nextLine().split(","))
                        .map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList());
                String csv3 = GenericCsvExporter.exportToCsv(es, efields);
                System.out.println("\n--- CSV ---\n" + csv3);
                break;
            default:
                System.out.println("Entidade desconhecida.");
        }
    }

    private void listActiveCourses() {
        List<Course> courses = service.listActiveCourses();
        if (courses.isEmpty()) System.out.println("Nenhum curso ativo.");
        courses.forEach(System.out::println);
    }

    private void listAllCourses() {
        service.allCourses().forEach(System.out::println);
    }

    private void logout() {
        System.out.println("Logout: " + currentUser.getEmail());
        currentUser = null;
    }
}
