package cli.admin;

import cli.Command;
import cli.util.InputReader;
import service.AdminService;

public class DeleteAppointmentCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public DeleteAppointmentCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("Введите id записи для удаления: ");
        admin.deleteAppointment(id);
        System.out.println("Удалено.");
    }

    @Override
    public String getCommandName() {
        return "Удалить запись";
    }
}
