package cli.doctor;

import cli.Command;
import cli.util.TablePrinter;
import domain.Appointment;
import service.AppointmentService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListAppointmentsCommand implements Command {

    private final long doctorId;
    private final AppointmentService service;

    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

    public ListAppointmentsCommand(long doctorId, AppointmentService service) {
        this.doctorId = doctorId;
        this.service = service;
    }

    @Override
    public void execute() {
        List<Appointment> list = service.getDoctorAppointments(doctorId);
        if (list.isEmpty()) {
            System.out.println("Записей нет.");
            return;
        }

        String[] headers = {"id", "pet_id", "doctor_id", "starts_at", "ends_at", "status", "reason"};
        List<String[]> rows = new ArrayList<>();

        for (Appointment a : list) {
            rows.add(new String[] {
                    String.valueOf(a.getId()),
                    String.valueOf(a.getPetId()),
                    String.valueOf(a.getDoctorId()),
                    a.getStartsAt() == null ? "" : a.getStartsAt().format(OUT_FMT),
                    a.getEndsAt() == null ? "" : a.getEndsAt().format(OUT_FMT),
                    String.valueOf(a.getStatus()),
                    a.getReason() == null ? "" : a.getReason()
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Показать мои записи";
    }
}
