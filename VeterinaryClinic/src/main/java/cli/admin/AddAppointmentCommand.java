package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Appointment;
import domain.AppointmentStatus;
import service.AdminService;

import java.time.OffsetDateTime;
import java.util.Locale;

public class AddAppointmentCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public AddAppointmentCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long petId = in.readLong("petId: ");
        long doctorId = in.readLong("doctorId: ");

        OffsetDateTime start = in.readOffsetDateTime("startsAt (timestamptz): ");
        OffsetDateTime end = in.readOffsetDateTime("endsAt (timestamptz): ");

        String reason = in.readLine("reason: ");
        String rawStatus = in.readLine("status (PLANNED, DONE, CANCELLED, NO_SHOW): ").toUpperCase(Locale.ROOT);

        AppointmentStatus status = AppointmentStatus.valueOf(rawStatus);

        Appointment appt = new Appointment(0L, petId, doctorId, start, end, reason, status);
        admin.addAppointment(appt);

        System.out.println("Запись добавлена.");
    }

    @Override
    public String getCommandName() {
        return "Appointments: добавить";
    }
}
