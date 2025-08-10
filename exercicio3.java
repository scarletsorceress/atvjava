import java.util.Random;
import java.util.Scanner;

public class exercicio3 {
    public static void main(String[] args) {
        Random rand = new Random();
        int numeroSecreto = rand.nextInt(100) + 1;
        Scanner scanner = new Scanner(System.in);
        int tentativas = 0;
        int palpite = 0;

        System.out.println("Adivinhe o número entre 1 e 100: ");

        while (palpite != numeroSecreto) {
            System.out.print("Qual seu palpite: ");
            palpite = scanner.nextInt();
            tentativas++;

            if (palpite < numeroSecreto) {
                System.out.println("Muito baixo");
            } else if (palpite > numeroSecreto) {
                System.out.println("Muito alto");
            }
        }

        System.out.println("Boa, você acertou em " + tentativas + " tentativas. o número era " + numeroSecreto);
        scanner.close();
    }
}