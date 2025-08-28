package aula1;
import java.util.Scanner;

public class exercicio6 {
    private double raio;
    
    public void setRaio(double raio) {
        this.raio = raio;
        if (raio <= 0) {
            throw new IllegalArgumentException("O raio não pode ser 0 e nem negativo.");
        }
    }

    public double getRaio() {
        return raio;
    }

    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    public static void main(String[] args) {
        exercicio6 circulo = new exercicio6();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o raio do círculo: ");
        double raio = scanner.nextDouble();
        circulo.setRaio(raio);
        System.out.println("A área do círculo é: " + circulo.calcularArea());
        scanner.close();
    }
}
