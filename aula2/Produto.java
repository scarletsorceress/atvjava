import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Produto {
    String nome;
    double preco;
    int categoria;

    Produto(String nome, double preco, int categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    //criando o método buscarProdutoPorNome(List<Produto> produtos, String nome) que retorna um Optional<Produto> usando filter e findFirst
    public static Optional<Produto> buscarProdutoPorNome(List<Produto> produtos, String nome) {
        return produtos.stream()
                .filter(p -> p.nome.equalsIgnoreCase(nome))
                .findFirst();
    }

    //criando categoria
    static final int ALIMENTO = 1;
    static final int LIMPEZA = 2;
    static final int ELETRONICO = 3;
    static final int MOVEIS = 4;
    static final int LIVROS = 5;


    public static class Teste {
        public static void main(String[] args) {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Arroz", 20.0, ALIMENTO));
        produtos.add(new Produto("Livro de Python", 15.0, LIVROS));
        produtos.add(new Produto("Smartphone", 1500.0, ELETRONICO));
        produtos.add(new Produto("Sofá", 800.0, MOVEIS));
        produtos.add(new Produto("Livro de Java", 50.0, LIVROS));
        produtos.add(new Produto("Feijão", 8.0, ALIMENTO));
        produtos.add(new Produto("Detergente", 5.0, LIMPEZA));
        produtos.add(new Produto("Notebook", 2500.0, ELETRONICO));
    
        //exibindo apenas os produtos de categoria eletronico
        System.out.println("Produtos da categoria ELETRONICO:");
        for (Produto p : produtos) {
            if (p.categoria == ELETRONICO) {
                System.out.println("Nome: " + p.nome + ", Preço: R$" + p.preco);
            }
        }

        //fazendo o mesmo usando stream e filter
        System.out.println("\nUsando stream() e filter():");
        produtos.stream()
                .filter(p -> p.categoria == ELETRONICO)
                .forEach(p -> System.out.println("Nome: " + p.nome + ", Preço: R$" + p.preco));
                
                
        //criando uma nova lista apenas apenas com os preços maiores que 500 usando apenas filter e map, sem toList
        System.out.println("\nPreços maiores que R$500:");
        produtos.stream()
                .filter(p -> p.preco > 500)
                .map(p -> p.preco)
                .forEach(preco -> System.out.println("Preço: R$" + preco));
                
        //calculando o total dos produtos de categoria LIVROS usando filter(), mapToDouble() e sum()
        System.out.println("\nTotal dos produtos da categoria LIVROS: R$" + produtos.stream()
                .filter(p -> p.categoria == LIVROS)
                .mapToDouble(p -> p.preco)
                .sum());
                
        //buscando um produto por nome usando o método buscarProdutoPorNome e tratando a exceção caso o produto não seja encontrado
        try {
            Produto p = buscarProdutoPorNome(produtos, "Notebook")
                    .orElseThrow(() -> new Exception("Produto não encontrado"));
            System.out.println("\nProduto encontrado: Nome: " + p.nome + ", Preço: R$" + p.preco);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
    }
    }
}
