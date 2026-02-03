package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Doctor;
import service.AdminService;
import service.validator.PhoneValidator;

public class UpdateDoctorCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public UpdateDoctorCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long id = in.readLong("doctorId: ");

        Doctor current = admin.getDoctorById(id);

        String fullName = in.readLine("fullName (enter = оставить): ");
        String specialization = in.readLine("specialization (enter = оставить): ");
        String phoneRaw = in.readLine("phone (enter = оставить): ");
        if (!phoneRaw.isBlank()) {
            String phone = PhoneValidator.validateAndNormalize(phoneRaw);
            if (phone.isBlank()) {
                System.out.println("Телефон неверный. Изменение отменено.");
            } else {
                current.setPhone(phone);
            }
        }

        if (!fullName.isBlank()) current.setFullName(fullName);
        if (!specialization.isBlank()) current.setSpecialization(specialization);

        admin.updateDoctor(id, current);
        System.out.println("Доктор обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Doctors: обновить";
    }
}
