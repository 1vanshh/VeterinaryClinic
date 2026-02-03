package cli.owner;

import cli.Command;
import cli.util.InputReader;
import service.AppointmentService;

import java.time.OffsetDateTime;

public class RescheduleAppointmentCommand implements Command {

    private final InputReader in;
    private final long ownerId;
    private final AppointmentService appointmentService;

    public RescheduleAppointmentCommand(InputReader in, long ownerId, AppointmentService appointmentService) {
        this.in = in;
        this.ownerId = ownerId;
        this.appointmentService = appointmentService;
    }

    @Override
    public void execute() {
        long apptId = in.readLong("appointmentId: ");

        OffsetDateTime newStart = in.readOffsetDateTime("newStartsAt (timestamptz): ");
        OffsetDateTime newEnd = in.readOffsetDateTime("newEndsAt (timestamptz): ");

        appointmentService.rescheduleByOwner(ownerId, apptId, newStart, newEnd);
        System.out.println("Запись перенесена.");
    }

    @Override
    public String getCommandName() {
        return "Запись к врачу: перенести";
    }
}
