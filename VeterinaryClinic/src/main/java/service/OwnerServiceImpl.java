package service;

import domain.Pet;
import repository.PetRepository;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {

    private final PetRepository petRepository;

    public OwnerServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void updateMyPet(long ownerId, long petId, Pet patch) {
        Pet existing = getMyPetById(ownerId, petId);

        if (patch.getName() != null) existing.setName(patch.getName());
        if (patch.getSpecies() != null) existing.setSpecies(patch.getSpecies());
        if (patch.getBreed() != null) existing.setBreed(patch.getBreed());
        if (patch.getGender() != null) existing.setGender(patch.getGender());

        petRepository.update(petId, existing);
    }

    @Override
    public Pet getMyPetById(long ownerId, long petId) {
        Pet pet = petRepository.findById(petId);
        if (pet == null) {
            throw new IllegalArgumentException("Питомец не найден: id=" + petId);
        }
        if (pet.getOwnerId() != ownerId) {
            throw new IllegalStateException("Нет доступа к чужому питомцу");
        }

        return pet;
    }

    @Override
    public List<Pet> getMyPets(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
