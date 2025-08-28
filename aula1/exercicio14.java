package aula1;
public class exercicio14 {
    public static void main(String[] args) {
        java.util.HashSet<String> emails = new java.util.HashSet<>();
        emails.add("bryan@gmail.com");
        emails.add("bryan@gmail.com");
        emails.add("raul@gmail.com");

        System.out.println("Emails cadastrados: " + emails);
        System.out.println("Total de emails cadastrados: " + emails.size());

    }
}
