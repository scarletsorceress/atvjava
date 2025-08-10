import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class exercicio10 {
    
    public static void main(String[] args){
        ArrayList<Integer> numeros = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 5 números inteiros:");

        //loop só pra não repetir o código 5 vezes
        for (int i = 0; i < 5; i++) {
            System.out.print("Número " + (i + 1) + ": ");
            int numero = scanner.nextInt();
            numeros.add(numero);
        }
        
        Collections.sort(numeros);
        System.out.println("Números em ordem crescente: " + numeros);
        scanner.close();
    }
}
