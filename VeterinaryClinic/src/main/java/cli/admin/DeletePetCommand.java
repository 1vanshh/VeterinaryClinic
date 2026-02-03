package cli.admin;

import cli.Command;
import cli.util.InputReader;
import service.AdminService;

public class DeletePetCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public DeletePetCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("petId: ");
        admin.deletePet(id);
        System.out.println("Питомец удалён.");
    }

    @Override
    public String getCommandName() {
        return "Pets: удалить";
    }
}
