package cli.admin;

import cli.BaseMenu;
import cli.util.InputReader;
import service.AdminService;

public class AdminMenu extends BaseMenu {

    private final AdminService admin;

    public AdminMenu(InputReader in, AdminService admin) {
        super(in);
        this.admin = admin;

        // Owners
        addCommand(new ListOwnersCommand(admin));
        addCommand(new AddOwnerCommand(in, admin));
        addCommand(new UpdateOwnerCommand(in, admin));
        addCommand(new DeleteOwnerCommand(in, admin));

        // Doctors
        addCommand(new ListDoctorsCommand(admin));
        addCommand(new AddDoctorCommand(in, admin));
        addCommand(new UpdateDoctorCommand(in, admin));
        addCommand(new DeleteDoctorCommand(in, admin));

        // Pets
        addCommand(new ListPetsCommand(admin));
        addCommand(new AddPetCommand(in, admin));
        addCommand(new UpdatePetCommand(in, admin));
        addCommand(new DeletePetCommand(in, admin));

        // Appointments
        addCommand(new ListAppointmentsCommand(admin));
        addCommand(new AddAppointmentCommand(in, admin));
        addCommand(new UpdateAppointmentCommand(in, admin));
        addCommand(new DeleteAppointmentCommand(in, admin));
        addCommand(new SetAppointmentStatusCommand(in, admin));
    }

    @Override
    protected void printHeader() {
        System.out.println("\n=== Профиль ADMIN ===");
        System.out.println("0 = на главный экран, -1 = выход");
    }
}
