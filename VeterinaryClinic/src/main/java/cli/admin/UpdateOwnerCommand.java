package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Owner;
import service.AdminService;
import service.validator.PhoneValidator;

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
        String phoneRaw = in.readLine("phone (enter = оставить): ");
        if (!phoneRaw.isBlank()) {
            String phone = PhoneValidator.validateAndNormalize(phoneRaw);
            if (phone.isBlank()) {
                System.out.println("Телефон неверный. Изменение отменено.");
            } else {
                current.setPhone(phone);
            }
        }
        String email = in.readLine("email (enter = оставить): ");
        String address = in.readLine("address (enter = оставить): ");

        if (!fullName.isBlank()) current.setFullName(fullName);
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
