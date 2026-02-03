package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Owner;
import service.AdminService;

public class AddOwnerCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public AddOwnerCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        String fullName = in.readLine("fullName: ");
        String phone = in.readPhone("phone: ");
        String email = in.readLine("email: ");
        String address = in.readLine("address: ");

        Owner owner = new Owner(0L, fullName, phone, email, address);
        admin.addOwner(owner);

        System.out.println("Владелец добавлен.");
    }

    @Override
    public String getCommandName() {
        return "Owners: добавить";
    }
}
