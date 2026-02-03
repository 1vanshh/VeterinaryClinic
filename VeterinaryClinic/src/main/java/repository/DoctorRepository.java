package repository;

import domain.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor,Long> {

    Doctor findByPhone(String phone);
}
