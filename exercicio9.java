import java.util.ArrayList;
import java.util.Scanner;

public class exercicio9 {
    public static void main(String[] args) {
        ArrayList<String> tarefas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n1. Adicionar tarefa");
            System.out.println("2. Remover tarefa");
            System.out.println("3. Listar tarefas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpando o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite a tarefa: ");
                    String tarefa = scanner.nextLine();
                    tarefas.add(tarefa);
                    System.out.println("Tarefa adicionada!");
                    break;
                case 2:
                    System.out.print("Digite o índice da tarefa para remover: ");
                    int indice = scanner.nextInt();
                    scanner.nextLine(); // limpando o buffer
                    if (indice >= 0 && (indice - 1) < tarefas.size()) {

                        //aqui eu preferi colocar o índice - 1 para que o usuário possa remover a tarefa pelo número que ele ve na lista
                        //porque eu acho meio estranho remover o item "0"
                        tarefas.remove(indice - 1);
                        System.out.println("Tarefa removida!");
                    } else {
                        System.out.println("Índice inválido!");
                    }
                    break;
                case 3:
                    System.out.println("Lista de tarefas:");
                    for (int i = 0; i < tarefas.size(); i++) {

                        //mesma coisa aqui, coloquei i + 1 para o usuário ver a lista a partir do item 1
                        System.out.println(i+1 + ": " + tarefas.get(i));
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
