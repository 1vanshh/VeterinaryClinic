package cli.doctor;

import cli.Command;
import cli.util.InputReader;
import domain.AppointmentStatus;
import service.AppointmentService;

import java.util.Locale;

public class SetAppointmentStatusCommand implements Command {

    private final InputReader in;
    private final long doctorId;
    private final AppointmentService service;

    public SetAppointmentStatusCommand(InputReader in, long doctorId, AppointmentService service) {
        this.in = in;
        this.doctorId = doctorId;
        this.service = service;
    }

    @Override
    public void execute() {
        long apptId = in.readLong("Введите id записи: ");

        String raw = in.readLine("Введите статус (PLANNED, DONE, CANCELLED): ")
                .toUpperCase(Locale.ROOT);

        AppointmentStatus status;
        try {
            status = AppointmentStatus.valueOf(raw);
        } catch (Exception e) {
            System.out.println("Неверный статус.");
            return;
        }

        service.updateStatusByDoctor(doctorId, apptId, status);
        System.out.println("Статус обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Изменить статус записи";
    }
}
