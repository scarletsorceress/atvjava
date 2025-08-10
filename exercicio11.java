import java.util.LinkedList;

public class exercicio11 {
    public static void main(String[] args) {
        LinkedList<String> pacientes = new LinkedList<>();
        pacientes.add("Bryan");
        pacientes.add("Raul");
        pacientes.add("Jonathan");
        pacientes.add("Arthur");
        pacientes.add("Gabriel");

        System.out.println("Lista de pacientes: " + pacientes);

        //"atendendo" o paciente mais antigo

        String pacienteAtendido = pacientes.removeFirst();
        System.out.println("\nPaciente atendido: " + pacienteAtendido);
        pacienteAtendido = pacientes.removeFirst();
        System.out.println("Paciente atendido: " + pacienteAtendido);
        System.out.println("\nLista de pacientes após atendimentos: " + pacientes);

        //adicionando um novo paciente prioritario
        System.out.println("\nAdicionando novos pacientes prioritários...\n");
        pacientes.addFirst("Carlos");
        pacientes.addFirst("Anaju");

        System.out.println("Lista de pacientes após adicionar novos: " + pacientes);
    }
}