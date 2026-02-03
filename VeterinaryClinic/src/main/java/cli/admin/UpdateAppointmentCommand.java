package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Appointment;
import service.AdminService;

import java.time.OffsetDateTime;

public class UpdateAppointmentCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public UpdateAppointmentCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("appointmentId: ");
        Appointment current = admin.getAppointmentById(id);

        String sPetId = in.readLine("petId (enter = оставить): ");
        String sDoctorId = in.readLine("doctorId (enter = оставить): ");

        String changeStart = in.readLine("Изменить startsAt? (y/n): ");
        if (changeStart.equalsIgnoreCase("y")) {
            OffsetDateTime start = in.readOffsetDateTime("startsAt (timestamptz): ");
            current.setStartsAt(start);
        }

        String changeEnd = in.readLine("Изменить endsAt? (y/n): ");
        if (changeEnd.equalsIgnoreCase("y")) {
            OffsetDateTime end = in.readOffsetDateTime("endsAt (timestamptz): ");
            current.setEndsAt(end);
        }

        String reason = in.readLine("reason (enter = оставить): ");

        if (!sPetId.isBlank()) {
            current = new Appointment(
                    current.getId(),
                    Long.parseLong(sPetId),
                    current.getDoctorId(),
                    current.getStartsAt(),
                    current.getEndsAt(),
                    current.getReason(),
                    current.getStatus()
            );
        }

        if (!sDoctorId.isBlank()) {
            current = new Appointment(
                    current.getId(),
                    current.getPetId(),
                    Long.parseLong(sDoctorId),
                    current.getStartsAt(),
                    current.getEndsAt(),
                    current.getReason(),
                    current.getStatus()
            );
        }

        if (!reason.isBlank()) current.setReason(reason);

        admin.updateAppointment(id, current);
        System.out.println("Запись обновлена.");
    }

    @Override
    public String getCommandName() {
        return "Appointments: обновить";
    }
}
