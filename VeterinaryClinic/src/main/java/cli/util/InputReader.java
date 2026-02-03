package cli.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    // Форматы, которые часто соответствуют timestamptz-вводу в PostgreSQL
    private static final List<DateTimeFormatter> TS_FORMATS = List.of(
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,                  // 2026-02-03T14:30:00+01:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX"),   // 2026-02-03 14:30:00+01:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mmXXX"),      // 2026-02-03 14:30+01:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"),     // 2026-02-03 14:30:00+01
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mmX")         // 2026-02-03 14:30+01
    );

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public long readLong(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public OffsetDateTime readOffsetDateTime(String prompt) {
        while (true) {
            String s = readLine(prompt);

            for (DateTimeFormatter fmt : TS_FORMATS) {
                try {
                    return OffsetDateTime.parse(s, fmt);
                } catch (DateTimeParseException ignored) {}
            }

            System.out.println("Неверный формат даты/времени.");
            System.out.println("Примеры (timestamptz):");
            System.out.println("- 2026-02-03T14:30:00+01:00");
            System.out.println("- 2026-02-03 14:30:00+01:00");
            System.out.println("- 2026-02-03 14:30+01:00");
        }
    }
}
