package cli.owner;

import cli.Command;
import cli.util.InputReader;
import service.AppointmentService;

import java.time.OffsetDateTime;

public class BookAppointmentCommand implements Command {

    private final InputReader in;
    private final long ownerId;
    private final AppointmentService appointmentService;

    public BookAppointmentCommand(InputReader in, long ownerId, AppointmentService appointmentService) {
        this.in = in;
        this.ownerId = ownerId;
        this.appointmentService = appointmentService;
    }

    @Override
    public void execute() {
        long petId = in.readLong("petId: ");
        long doctorId = in.readLong("doctorId: ");

        OffsetDateTime start = in.readOffsetDateTime("startsAt (timestamptz): ");
        OffsetDateTime end = in.readOffsetDateTime("endsAt (timestamptz): ");

        String reason = in.readLine("reason: ");

        appointmentService.book(ownerId, petId, doctorId, start, end, reason);
        System.out.println("Вы записаны на приём.");
    }

    @Override
    public String getCommandName() {
        return "Запись к врачу: создать";
    }
}
