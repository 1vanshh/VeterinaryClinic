package cli.owner;

import cli.BaseMenu;
import cli.util.InputReader;
import service.AdminService;
import service.AppointmentService;
import service.OwnerService;

public class OwnerMenu extends BaseMenu {

    private final long ownerId;
    private final OwnerService ownerService;
    private final AppointmentService appointmentService;
    private final AdminService adminService;

    public OwnerMenu(InputReader in,
                     long ownerId,
                     OwnerService ownerService,
                     AppointmentService appointmentService,
                     AdminService adminService) {
        super(in);
        this.ownerId = ownerId;
        this.ownerService = ownerService;
        this.appointmentService = appointmentService;
        this.adminService = adminService;

        addCommand(new ViewMyAppointmentsCommand(ownerId, appointmentService, ownerService));

        addCommand(new ListMyPetsCommand(ownerId, ownerService));
        addCommand(new UpdateMyPetCommand(in, ownerId, ownerService));

        addCommand(new ListDoctorsCommand(adminService));

        addCommand(new BookAppointmentCommand(in, ownerId, appointmentService));
        addCommand(new RescheduleAppointmentCommand(in, ownerId, appointmentService));
        addCommand(new CancelAppointmentCommand(in, ownerId, appointmentService));
    }

    @Override
    protected void printHeader() {
        System.out.println("\n=== Профиль OWNER (id=" + ownerId + ") ===");
        System.out.println("0 = на главный экран, -1 = выход");
    }
}
