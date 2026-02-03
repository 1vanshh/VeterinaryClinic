package repository;

import domain.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByOwnerId(long ownerId);
}
