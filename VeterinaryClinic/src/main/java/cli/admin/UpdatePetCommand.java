package cli.admin;

import cli.Command;
import cli.util.InputReader;
import domain.Gender;
import domain.Pet;
import service.AdminService;

import java.util.Locale;

public class UpdatePetCommand implements Command {

    private final InputReader in;
    private final AdminService admin;

    public UpdatePetCommand(InputReader in, AdminService admin) {
        this.in = in;
        this.admin = admin;
    }

    @Override
    public void execute() {
        long petId = in.readLong("petId: ");

        Pet current = admin.getPetById(petId);

        String name = in.readLine("name (enter = оставить): ");
        String species = in.readLine("species (enter = оставить): ");
        String breed = in.readLine("breed (enter = оставить): ");
        String g = in.readLine("gender MALE/FEMALE (enter = оставить): ").toUpperCase(Locale.ROOT);

        if (!name.isBlank()) current.setName(name);
        if (!species.isBlank()) current.setSpecies(species);
        if (!breed.isBlank()) current.setBreed(breed);
        if (!g.isBlank()) current.setGender(Gender.valueOf(g));

        admin.updatePet(petId, current);
        System.out.println("Питомец обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Pets: обновить";
    }
}
