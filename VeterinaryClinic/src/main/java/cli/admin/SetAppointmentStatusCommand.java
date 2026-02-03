package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Appointment;
import domain.AppointmentStatus;
import service.AdminService;

import java.util.Locale;

public class SetAppointmentStatusCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public SetAppointmentStatusCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("appointmentId: ");
        String raw = in.readLine("status (PLANNED, DONE, CANCELLED, NO_SHOW): ").toUpperCase(Locale.ROOT);

        AppointmentStatus status = AppointmentStatus.valueOf(raw);

        Appointment appt = admin.getAppointmentById(id);
        appt.setStatus(status);
        admin.updateAppointment(id, appt);

        System.out.println("Статус обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Appointments: изменить статус";
    }
}
