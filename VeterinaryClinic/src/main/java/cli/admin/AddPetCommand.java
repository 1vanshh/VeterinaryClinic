package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Gender;
import domain.Pet;
import service.AdminService;

import java.util.Locale;

public class AddPetCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public AddPetCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long ownerId = in.readLong("ownerId: ");
        String name = in.readLine("name: ");
        String species = in.readLine("species: ");
        String breed = in.readLine("breed: ");
        String g = in.readLine("gender (MALE/FEMALE или unknown): ").toUpperCase(Locale.ROOT);

        Gender gender = null;
        if (!g.isBlank()) {
            gender = Gender.valueOf(g);
        }

        Pet pet = new Pet(0L, ownerId, name, species, breed, gender);
        admin.addPet(pet);

        System.out.println("Питомец добавлен.");
    }

    @Override
    public String getCommandName() {
        return "Pets: добавить";
    }
}
