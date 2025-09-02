package dev.academiadev.util;

import java.lang.reflect.Field;
import java.util.*;

public class GenericCsvExporter {
    public static String exportToCsv(List<?> objects, List<String> fieldNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", fieldNames)).append("\n");
        for (Object obj : objects) {
            List<String> cells = new ArrayList<>();
            for (String fName : fieldNames) {
                try {
                    Field f = getFieldRecursively(obj.getClass(), fName);
                    f.setAccessible(true);
                    Object val = f.get(obj);
                    cells.add(escapeCsv(val == null ? "" : val.toString()));
                } catch (NoSuchFieldException nsfe) {
                    cells.add("");
                } catch (IllegalAccessException iae) {
                    cells.add("");
                }
            }
            sb.append(String.join(",", cells)).append("\n");
        }
        return sb.toString();
    }

    private static Field getFieldRecursively(Class<?> cls, String fieldName) throws NoSuchFieldException {
        Class<?> current = cls;
        while (current != null) {
            try { return current.getDeclaredField(fieldName); } catch (NoSuchFieldException ex) { current = current.getSuperclass(); }
        }
        throw new NoSuchFieldException(fieldName);
    }

    private static String escapeCsv(String s) {
        if (s.contains(",") || s.contains("\n") || s.contains("\"")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }
}
