package aula1;
import java.util.Scanner;

public class atv1{
    public static void main(String[] args){
        
        Scanner scan = new Scanner(System.in);

        float[] notas = new float[3];
        float soma = 0;

        for(int i = 0; i < 3; i++) {
            System.out.printf("Manda sua nota %d:%n", i + 1);
            notas[i] = scan.nextFloat();
            soma += notas[i];
        }

        float media = soma / 3;
        System.out.printf("Sua nota final é %f\n", media);

        if(media >= 5 && media < 7){
            System.out.println("Recuperação");
        } else if(media >= 7){
            System.out.println("Aprovado");
        } else if(media < 5){
            System.out.println("Reprovado");
        }
    }
}