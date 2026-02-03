package cli.owner;

import cli.Command;
import cli.util.InputReader;
import domain.Gender;
import domain.Pet;
import service.OwnerService;

import java.util.Locale;

public class UpdateMyPetCommand implements Command {

    private final InputReader in;
    private final long ownerId;
    private final OwnerService ownerService;

    public UpdateMyPetCommand(InputReader in, long ownerId, OwnerService ownerService) {
        this.in = in;
        this.ownerId = ownerId;
        this.ownerService = ownerService;
    }

    @Override
    public void execute() {
        long petId = in.readLong("petId: ");

        String name = in.readLine("name (enter = оставить): ");
        String species = in.readLine("species (enter = оставить): ");
        String breed = in.readLine("breed (enter = оставить): ");
        String g = in.readLine("gender MALE/FEMALE (enter = оставить): ").toUpperCase(Locale.ROOT);

        Gender gender = null;
        if (!g.isBlank()) gender = Gender.valueOf(g);

        // patch: null/blank поля не трогаются в сервисе
        Pet patch = new Pet(petId, ownerId,
                name.isBlank() ? null : name,
                species.isBlank() ? null : species,
                breed.isBlank() ? null : breed,
                gender);

        ownerService.updateMyPet(ownerId, petId, patch);
        System.out.println("Питомец обновлён.");
    }

    @Override
    public String getCommandName() {
        return "Мои питомцы: редактировать";
    }
}
