import java.util.HashMap;
import java.util.Scanner;

public class exercicio18 {
    public static void main(String[] args) {
        HashMap<String, String> palavras = new HashMap<>();
        palavras.put("gladiator", "gladiador");
        palavras.put("mage", "mago");
        palavras.put("bard", "bardo");
        palavras.put("warrior", "guerreiro");
        palavras.put("knight", "cavaleiro");

        System.out.println("Insira uma palavra, e se ela existir no mapa, será exibida sua tradução:");
        Scanner scanner = new java.util.Scanner(System.in);
        String palavra = scanner.nextLine();
        String traducao = palavras.get(palavra);
        if (traducao != null) {
            System.out.println(traducao);
        } else {
            System.out.println("Palavra não encontrada no mapa.");
        }
        scanner.close();}
}
