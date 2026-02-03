package cli.owner;

import cli.Command;
import cli.util.TablePrinter;
import domain.Doctor;
import service.AdminService;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorsCommand implements Command {

    private final AdminService admin;

    public ListDoctorsCommand(AdminService admin) {
        this.admin = admin;
    }

    @Override
    public void execute() {
        List<Doctor> list = admin.getAllDoctors();
        if (list.isEmpty()) {
            System.out.println("Докторов нет.");
            return;
        }

        String[] headers = {"id", "full_name", "specialization", "phone"};
        List<String[]> rows = new ArrayList<>();

        for (Doctor d : list) {
            rows.add(new String[] {
                    String.valueOf(d.getId()),
                    d.getFullName(),
                    d.getSpecialization(),
                    d.getPhone()
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Доктора: показать список";
    }
}
