import java.util.Scanner;

public class exercicio7 {
    protected String marca;
    protected String modelo;

    @Override
    public String toString() {
        return "Marca: " + marca + ", Modelo: " + modelo;
    }

    public class Carro extends exercicio7 {
        private int numeroPortas;

        public Carro(String marca, String modelo, int numeroPortas) {
            this.marca = marca;
            this.modelo = modelo;
            this.numeroPortas = numeroPortas;
        }

        @Override
        public String toString() {
            return super.toString() + ", número de portas: " + numeroPortas;
        }

        public String getMarca() {
            return marca;
        }

        public String getModelo() {
            return modelo;
        }

        public int getNumeroPortas() {
            return numeroPortas;
        }
    }

    public class Moto extends exercicio7 {
        private int numeroCilindradas;

        public Moto(String marca, String modelo, int numeroCilindradas) {
            this.marca = marca;
            this.modelo = modelo;
            this.numeroCilindradas = numeroCilindradas;
        }

        @Override
        public String toString() {
            return super.toString() + ", numero de cilindradas: " + numeroCilindradas + "cc";
        }

        public String getMarca() {
            return marca;
        }

        public String getModelo() {
            return modelo;
        }

        public int getNumeroCilindradas() {
            return numeroCilindradas;
        }
    }

    public static void main(String[] args) {
        exercicio7 exercicio = new exercicio7();
        Scanner scanner = new Scanner(System.in);

        exercicio7[] veiculos = new exercicio7[3]; // exemplo com 3 veículos

        for (int i = 0; i < veiculos.length; i++) {
            System.out.print("Digite o tipo (carro/moto): ");
            String tipo = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            if (tipo.equalsIgnoreCase("carro")) {
                System.out.print("numero de portas: ");
                int portas = Integer.parseInt(scanner.nextLine());
                veiculos[i] = exercicio.new Carro(marca, modelo, portas);
            } else if (tipo.equalsIgnoreCase("moto")) {
                System.out.print("numero de cilindradas: ");
                int cilindradas = Integer.parseInt(scanner.nextLine());
                veiculos[i] = exercicio.new Moto(marca, modelo, cilindradas);
            } else {
                System.out.println("Tipo invalido.");
                i--; // repete a leitura para este índice
            }
        }

        System.out.println("\nVeículos cadastrados:");
        for (exercicio7 veiculo : veiculos) {
            System.out.println(veiculo);
        }

        scanner.close();
    }
}