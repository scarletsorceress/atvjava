package aula1;
import java.util.TreeSet;

public class exercicio16 {
    public static void main(String[] args) {
        TreeSet<String> nomes = new TreeSet<>();

        nomes.add("bryan");
        nomes.add("raul");
        nomes.add("arthur");
        nomes.add("gabriel"); 

        System.out.println("Nomes no TreeSet (ordem alfabética): " + nomes);
    }
}
