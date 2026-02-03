package cli.admin;

import cli.Command;
import cli.util.TablePrinter;
import domain.Owner;
import service.AdminService;

import java.util.ArrayList;
import java.util.List;

public class ListOwnersCommand implements Command {

    private final AdminService admin;

    public ListOwnersCommand(AdminService admin) {
        this.admin = admin;
    }

    @Override
    public void execute() {
        List<Owner> list = admin.getAllOwners();
        if (list.isEmpty()) {
            System.out.println("Владельцев нет.");
            return;
        }

        String[] headers = {"id", "full_name", "phone", "email", "address"};
        List<String[]> rows = new ArrayList<>();

        for (Owner o : list) {
            rows.add(new String[] {
                    String.valueOf(o.getId()),
                    o.getFullName(),
                    o.getPhone(),
                    o.getEmail(),
                    o.getAddress()
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Owners: список";
    }
}
