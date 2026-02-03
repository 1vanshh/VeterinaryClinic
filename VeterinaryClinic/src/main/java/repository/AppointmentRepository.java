package repository;

import domain.Appointment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(long doctorId);
    List<Appointment> findByPetId(long petId);
    boolean existsByDoctorAndTime(long doctorId, OffsetDateTime startsAt);
    boolean existsOverlapping(long doctorId, OffsetDateTime start, OffsetDateTime end);
}
