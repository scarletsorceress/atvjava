package aula1;
import java.util.Scanner;

public class exercicio8 {
    public static class SaldoInsuficienteException extends Exception {
        public SaldoInsuficienteException(String mensagem) {
            super(mensagem);
        }
    }

    public static class ContaBancaria {
        private double saldo;

        public ContaBancaria(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        public void sacar(double valor) throws SaldoInsuficienteException {
            if (valor > saldo) {
                throw new SaldoInsuficienteException("Saldo insuficiente para saque de R$" + valor);
            }
            saldo -= valor;
        }

        public double getSaldo() {
            return saldo;
        }
    }

    public static void main(String[] args) {
        ContaBancaria conta = new ContaBancaria(100.0);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o valor do saque: ");
        double valorSaque = scanner.nextDouble();

        try {
            conta.sacar(valorSaque);
            System.out.println("Saque realizado com sucesso!");

        } catch (SaldoInsuficienteException e) { // e de "event" ou "exception" 
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("Saldo atual: R$" + conta.getSaldo());
        scanner.close();
    }
}
