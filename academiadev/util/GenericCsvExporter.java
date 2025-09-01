import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GenericCsvExporter {

    /**
     * Gera um CSV (String) a partir de uma lista de objetos.
     * @param items lista de objetos
     * @param fieldNames nomes dos campos (attributes) que queremos exportar — ex: "name","email"
     * @param <T> tipo
     * @return String formatada como CSV (header + linhas)
     */
    public static <T> String exportToCsv(List<T> items, List<String> fieldNames) {
        if (items == null) items = Collections.emptyList();
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append(String.join(",", fieldNames)).append("\n");

        for (T item : items) {
            List<String> row = new ArrayList<>();
            for (String fname : fieldNames) {
                String cell = "";
                try {
                    Field f = findField(item.getClass(), fname);
                    f.setAccessible(true);
                    Object val = f.get(item);
                    cell = val == null ? "" : val.toString().replace("\"", "\"\""); // escape quotes
                    // Surround with quotes if contains comma or newline
                    if (cell.contains(",") || cell.contains("\n") || cell.contains("\"")) {
                        cell = "\"" + cell + "\"";
                    }
                } catch (Exception e) {
                    cell = ""; // field not found or inaccessible -> leave empty
                }
                row.add(cell);
            }
            sb.append(String.join(",", row)).append("\n");
        }
        return sb.toString();
    }

    private static Field findField(Class<?> clazz, String name) throws NoSuchFieldException {
        Class<?> cur = clazz;
        while (cur != null) {
            try {
                return cur.getDeclaredField(name);
            } catch (NoSuchFieldException ex) {
                cur = cur.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }
}
