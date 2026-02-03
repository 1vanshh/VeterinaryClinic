package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Doctor;
import service.AdminService;

public class AddDoctorCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public AddDoctorCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        String fullName = in.readLine("fullName: ");
        String specialization = in.readLine("specialization: ");
        String phone = in.readPhone("phone: ");

        Doctor doctor = new Doctor(0L, fullName, specialization, phone);
        admin.addDoctor(doctor);

        System.out.println("Доктор добавлен.");
    }

    @Override
    public String getCommandName() {
        return "Doctors: добавить";
    }
}
