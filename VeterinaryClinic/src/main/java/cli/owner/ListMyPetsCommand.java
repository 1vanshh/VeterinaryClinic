package cli.owner;

import cli.Command;
import cli.util.TablePrinter;
import domain.Pet;
import service.OwnerService;

import java.util.ArrayList;
import java.util.List;

public class ListMyPetsCommand implements Command {

    private final long ownerId;
    private final OwnerService ownerService;

    public ListMyPetsCommand(long ownerId, OwnerService ownerService) {
        this.ownerId = ownerId;
        this.ownerService = ownerService;
    }

    @Override
    public void execute() {
        List<Pet> list = ownerService.getMyPets(ownerId);
        if (list.isEmpty()) {
            System.out.println("У вас нет питомцев.");
            return;
        }

        String[] headers = {"id", "name", "species", "breed", "gender"};
        List<String[]> rows = new ArrayList<>();

        for (Pet p : list) {
            rows.add(new String[] {
                    String.valueOf(p.getId()),
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
        return "Мои питомцы: список";
    }
}
