package repository;

import domain.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByPhone(String phone);
}
