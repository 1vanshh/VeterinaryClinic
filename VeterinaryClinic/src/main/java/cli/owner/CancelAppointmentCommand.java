package cli.owner;

import cli.Command;
import cli.util.InputReader;
import service.AppointmentService;

public class CancelAppointmentCommand implements Command {

    private final InputReader in;
    private final long ownerId;
    private final AppointmentService appointmentService;

    public CancelAppointmentCommand(InputReader in, long ownerId, AppointmentService appointmentService) {
        this.in = in;
        this.ownerId = ownerId;
        this.appointmentService = appointmentService;
    }

    @Override
    public void execute() {
        long apptId = in.readLong("appointmentId: ");
        appointmentService.cancelByOwner(ownerId, apptId);
        System.out.println("Запись отменена.");
    }

    @Override
    public String getCommandName() {
        return "Запись к врачу: отменить";
    }
}
