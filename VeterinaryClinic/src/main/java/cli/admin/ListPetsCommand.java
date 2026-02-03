package cli.admin;

import cli.Command;
import cli.util.TablePrinter;
import domain.Pet;
import service.AdminService;

import java.util.ArrayList;
import java.util.List;

public class ListPetsCommand implements Command {

    private final AdminService admin;

    public ListPetsCommand(AdminService admin) {
        this.admin = admin;
    }

    @Override
    public void execute() {
        List<Pet> list = admin.getAllPets();
        if (list.isEmpty()) {
            System.out.println("Питомцев нет.");
            return;
        }

        String[] headers = {"id", "owner_id", "name", "species", "breed", "gender"};
        List<String[]> rows = new ArrayList<>();

        for (Pet p : list) {
            rows.add(new String[] {
                    String.valueOf(p.getId()),
                    String.valueOf(p.getOwnerId()),
                    p.getName(),
                    p.getSpecies(),
                    p.getBreed(),
                    p.getGender() == null ? "" : p.getGender().name()
            });
        }

        TablePrinter.print(headers, rows);
    }

    @Override
    public String getCommandName() {
        return "Pets: список";
    }
}
