package aula1;
import java.util.HashMap;
import java.util.Scanner;

public class exercicio20 {
     
    public static void main(String[] args){
        HashMap<String, String> agenda = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n1 - Adicionar contato");
            System.out.println("2 - Buscar pelo nome");
            System.out.println("3 - Listar todos os contatos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção (1|2|3|4): ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    agenda.put(nome, telefone);
                    System.out.println("Contato adicionado!");
                    break;
                case 2:
                    System.out.print("Nome para buscar: ");
                    String busca = scanner.nextLine();
                    if (agenda.containsKey(busca)) {
                        System.out.println("Telefone: " + agenda.get(busca));
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 3:
                    System.out.println("Contatos na agenda:");
                    for (String n : agenda.keySet()) {
                        System.out.println(n + " - " + agenda.get(n));
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
