package aula1;
import java.util.LinkedHashSet;

public class exercicio15 {
    public static void main(String[] args) {
        LinkedHashSet<String> diasSemana = new LinkedHashSet<>();

        diasSemana.add("quarta");
        diasSemana.add("quinta");
        diasSemana.add("segunda");
        diasSemana.add("sabado");
        diasSemana.add("sexta");
        diasSemana.add("domingo");
        diasSemana.add("terça"); 

        System.out.println("diasSemana no LinkedHashSet: " + diasSemana);
    }
}
