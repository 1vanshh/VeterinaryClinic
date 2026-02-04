package cli.doctor;

import cli.Command;
import cli.util.TablePrinter;
import domain.Pet;
import service.AppointmentService;

import java.util.ArrayList;
import java.util.List;

public class ViewMyPatientsCommand implements Command {

    private final long doctorId;
    private final AppointmentService appointmentService;

    public ViewMyPatientsCommand(long doctorId, AppointmentService appointmentService) {
        this.doctorId = doctorId;
        this.appointmentService = appointmentService;
    }

    @Override
    public void execute() {
        List<Pet> patients = appointmentService.getDoctorPatients(doctorId);

        if (patients.isEmpty()) {
            System.out.println("У вас пока нет пациентов.");
            return;
        }

        System.out.println("Ваши пациенты (" + patients.size() + "):");

        String[] headers = {"ID", "Имя", "Вид", "Порода", "Владелец ID"};
        List<String[]> rows = new ArrayList<>();

        for (Pet pet : patients) {
            rows.add(new String[]{
                    String.valueOf(pet.getId()),
                    pet.getName(),
                    pet.getSpecies(),
                    pet.getBreed(),
                    String.valueOf(pet.getOwnerId())
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Мои пациенты";
    }
}