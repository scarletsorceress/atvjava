package aula1;
import java.util.HashMap;

public class exercicio19 {
    public static void main(String[] args) {
        String paragrafo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        HashMap<String, Integer> palavra = new HashMap<>();

        // Remove pontuação e transforma em minúsculas
        String[] palavras = paragrafo.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        for (String p : palavras) {
            palavra.put(p, palavra.getOrDefault(p, 0) + 1);
        }

        for (String p : palavra.keySet()) {
            System.out.println(p + ": " + palavra.get(p));
        }
    }
}
