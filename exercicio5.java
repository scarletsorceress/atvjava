public class exercicio5 {
    private String marca;
    private String modelo;
    private int ano;

    public exercicio5(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public void exibirInfo() {
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Ano: " + ano);
    }

    public static void main(String[] args) {
        exercicio5 carro = new exercicio5("Toyota", "Corolla", 2020);
        carro.exibirInfo();
    }
}
