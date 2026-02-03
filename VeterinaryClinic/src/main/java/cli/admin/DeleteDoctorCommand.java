package cli.admin;

import cli.Command;
import cli.util.InputReader;
import service.AdminService;

public class DeleteDoctorCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public DeleteDoctorCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("doctorId: ");
        admin.deleteDoctor(id);
        System.out.println("Доктор удалён.");
    }

    @Override
    public String getCommandName() {
        return "Doctors: удалить";
    }
}
