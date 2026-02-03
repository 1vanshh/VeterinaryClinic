package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Owner;
import service.AdminService;

public class UpdateOwnerCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public UpdateOwnerCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("ownerId: ");

        Owner current = admin.getOwnerById(id);

        String fullName = in.readLine("fullName (enter = оставить): ");
        String phone = in.readLine("phone (enter = оставить): ");
        String email = in.readLine("email (enter = оставить): ");
        String address = in.readLine("address (enter = оставить): ");

        if (!fullName.isBlank()) current.setFullName(fullName);
        if (!phone.isBlank()) current.setPhone(phone);
        if (!email.isBlank()) current.setEmail(email);
        if (!address.isBlank()) current.setAddress(address);

        admin.updateOwner(id, current);
        System.out.println("Владелец обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Owners: обновить";
    }
}
