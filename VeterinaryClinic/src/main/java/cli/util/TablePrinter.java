package cli.util;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {

    public static void print(String[] headers, List<String[]> rows) {
        int cols = headers.length;
        int[] widths = new int[cols];

        for (int i = 0; i < cols; i++) {
            widths[i] = headers[i].length();
        }
        for (String[] row : rows) {
            for (int i = 0; i < cols; i++) {
                String cell = safe(row, i);
                widths[i] = Math.max(widths[i], cell.length());
            }
        }

        String border = border(widths);
        System.out.println(border);
        System.out.println(rowLine(headers, widths));
        System.out.println(border);

        for (String[] row : rows) {
            System.out.println(rowLine(row, widths));
        }

        System.out.println(border);
    }

    private static String border(int[] widths) {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int w : widths) {
            sb.append("-".repeat(w + 2)).append("+");
        }
        return sb.toString();
    }

    private static String rowLine(String[] row, int[] widths) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < widths.length; i++) {
            String cell = safe(row, i);
            sb.append(" ").append(padRight(cell, widths[i])).append(" |");
        }
        return sb.toString();
    }

    private static String safe(String[] row, int i) {
        if (row == null || i >= row.length || row[i] == null) return "";
        return row[i];
    }

    private static String padRight(String s, int width) {
        if (s.length() >= width) return s;
        return s + " ".repeat(width - s.length());
    }
}
