package cli.doctor;

import cli.BaseMenu;
import cli.util.InputReader;
import service.AppointmentService;

public class DoctorMenu extends BaseMenu {

    private final long doctorId;
    private final AppointmentService appointmentService;

    public DoctorMenu(InputReader in, long doctorId, AppointmentService appointmentService) {
        super(in);
        this.doctorId = doctorId;
        this.appointmentService = appointmentService;

        addCommand(new ListMyAppointmentsCommand(doctorId, appointmentService));
        addCommand(new SetAppointmentStatusCommand(in, doctorId, appointmentService));
        addCommand(new ListAppointmentsCommand(doctorId, appointmentService));
        addCommand(new SetStatusCommand(in, doctorId, appointmentService));
    }

    @Override
    protected void printHeader() {
        System.out.println("\n=== Профиль ДОКТОРА (id=" + doctorId + ") ===");
        System.out.println("0 = на главный экран, -1 = выход");
    }
}
