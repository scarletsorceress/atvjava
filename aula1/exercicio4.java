package aula1;
public class exercicio4 {
    public static void main(String[] args) {
        int[] numeros = {2, 5, 7, 9, 3, 12, 16};
        int somaImpares = 0;
        for (int numero : numeros) {
            if (numero % 2 != 0) {
                somaImpares += numero;
            }
        }
        System.out.println("Soma dos números ímpares: " + somaImpares);
    }
}
