package repository;

import domain.Pet;

import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findByOwnerId(long ownerId);
}
