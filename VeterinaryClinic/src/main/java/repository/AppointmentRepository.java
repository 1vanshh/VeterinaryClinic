package repository;

import domain.Appointment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(long doctorId);
    boolean existsByDoctorAndTime(long doctorId, OffsetDateTime startsAt);
}
