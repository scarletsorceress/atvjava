package aula1;
import java.util.ArrayList;
import java.util.Scanner;

public class exercicio12 {
    public static void main(String[] args) {

        ArrayList<String> cidades = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        cidades.add("Sao Paulo");
        cidades.add("Rio de Janeiro");
        cidades.add("Belo Horizonte");
        cidades.add("Curitiba");
        cidades.add("Porto Alegre");
        cidades.add("Salvador");
        cidades.add("Fortaleza");
        cidades.add("Brasilia");

        System.out.println("Digite o nome de uma cidade para verificar se ela está na lista:");
        String cidade = scanner.nextLine();

        int indice = -1;
        for (int i = 0; i < cidades.size(); i++) {
            if (cidades.get(i).equalsIgnoreCase(cidade)) { // isso é para ignorar maiúsculas/minúsculas, fiz porque me irritava
                indice = i;                                // ter que colocar exatamente igual o nome da cidade
                break;
            }
        }

        if (indice != -1) {
            System.out.println("A cidade " + cidades.get(indice) + " está na lista, seu índice é: " + indice);
        } else {
            System.out.println("A cidade " + cidade + " não está na lista.");
        }

        scanner.close();
    }
}
