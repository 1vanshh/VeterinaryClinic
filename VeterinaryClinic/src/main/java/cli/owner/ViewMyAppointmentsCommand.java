package cli.owner;

import cli.Command;
import cli.util.TablePrinter;
import domain.Appointment;
import domain.Pet;
import service.AppointmentService;
import repository.PetRepository;
import service.OwnerService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewMyAppointmentsCommand implements Command {

    private final long ownerId;
    private final AppointmentService appointmentService;
    private final OwnerService ownerService;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public ViewMyAppointmentsCommand(long ownerId, AppointmentService appointmentService,
                                     OwnerService ownerService) {
        this.ownerId = ownerId;
        this.appointmentService = appointmentService;
        this.ownerService = ownerService;
    }

    @Override
    public void execute() {
        List<Pet> ownerPets = ownerService.getMyPets(ownerId);
        Map<Long, String> petNames = new HashMap<>();
        for (Pet pet : ownerPets) {
            petNames.put(pet.getId(), pet.getName());
        }

        List<Appointment> appointments = appointmentService.getOwnerAppointments(ownerId);

        if (appointments.isEmpty()) {
            System.out.println("У вас пока нет записей.");
            return;
        }

        System.out.println("Мои записи (" + appointments.size() + "):");

        String[] headers = {"ID", "Питомец", "Врач ID", "Дата", "Время", "Статус", "Причина"};
        List<String[]> rows = new ArrayList<>();

        for (Appointment appt : appointments) {
            String petName = petNames.getOrDefault(appt.getPetId(), "ID:" + appt.getPetId());
            String date = appt.getStartsAt().format(DATE_FORMAT);
            String time = appt.getStartsAt().format(TIME_FORMAT);
            String reason = appt.getReason() != null ?
                    (appt.getReason().length() > 20 ? appt.getReason().substring(0, 20) + "..." : appt.getReason())
                    : "";

            rows.add(new String[]{
                    String.valueOf(appt.getId()),
                    petName,
                    String.valueOf(appt.getDoctorId()),
                    date,
                    time,
                    appt.getStatus().toString(),
                    reason
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Мои записи";
    }
}