package service;

import domain.Pet;

import java.util.List;

public interface OwnerService {

    List<Pet> getMyPets(long ownerId);
    Pet getMyPetById(long ownerId, long petId);
    void updateMyPet(long ownerId, long petId, Pet patch);
}
