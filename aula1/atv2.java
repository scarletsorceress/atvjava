package aula1;
import java.util.Scanner;

public class atv2 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.println("Diga o número que você quer a tabuada: ");
        int numero = scan.nextInt();
        
        for(int i = 0; i <= 10; i++){
            int resultado = numero*i;
            System.out.printf("%d x %d = %d\n", numero, i, resultado);
        }
    }
}
