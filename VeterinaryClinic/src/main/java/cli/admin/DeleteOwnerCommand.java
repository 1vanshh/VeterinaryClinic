package cli.admin;

import cli.Command;
import cli.util.InputReader;
import service.AdminService;

public class DeleteOwnerCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public DeleteOwnerCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("ownerId: ");
        admin.deleteOwner(id);
        System.out.println("Владелец удалён.");
    }

    @Override
    public String getCommandName() {
        return "Owners: удалить";
    }
}
