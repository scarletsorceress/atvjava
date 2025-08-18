import java.util.TreeSet;

public class exercicio17 {
    public static class Produto implements Comparable<Produto> {
        private String nome;
        private double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        @Override
        public int compareTo(Produto outro) {
            return Double.compare(this.preco, outro.preco);
        }

        @Override
        public String toString() {
            return nome + " - R$" + preco;
        }
    }

    public static void main(String[] args) {
        TreeSet<Produto> produtos = new TreeSet<>();

        produtos.add(new Produto("Arroz", 20.0));
        produtos.add(new Produto("Feijão", 10.0));
        produtos.add(new Produto("Macarrão", 8.5));
        produtos.add(new Produto("Óleo", 12.0));

        System.out.println("Produtos ordenados por preço:");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }
}