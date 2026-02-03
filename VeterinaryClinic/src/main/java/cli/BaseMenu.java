package cli;

import cli.util.InputReader;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMenu {
    private final List<Command> commands = new ArrayList<>();
    protected final InputReader in;

    protected BaseMenu(InputReader in) {
        this.in = in;
    }

    protected void addCommand(Command c) {
        commands.add(c);
    }

    public void run() {
        printHeader();
        while (true) {
            printCommands();

            int choice = in.readInt("Введите индекс команды (0 = на главный, -1 = выход): ");

            if (choice == -1) {
                System.out.println("Завершение программы.");
                System.exit(0);
            }
            if (choice == 0) {
                return; // вернуться на welcome
            }

            if (choice < 1 || choice > commands.size()) {
                System.out.println("Нет команды с таким индексом.");
                continue;
            }

            Command cmd = commands.get(choice - 1);
            try {
                cmd.execute();
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printCommands() {
        System.out.println("\nКоманды:");
        for (int i = 0; i < commands.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, commands.get(i).getCommandName());
        }
    }

    protected abstract void printHeader();
}
