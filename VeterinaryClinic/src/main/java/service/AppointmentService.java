package service;

import domain.Appointment;
import domain.AppointmentStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentService {
    void book(long ownerId, long petId, long doctorId,
              OffsetDateTime start, OffsetDateTime end,
              String reason);

    void rescheduleByOwner(long ownerId, long appointmentId,
                           OffsetDateTime newStart, OffsetDateTime newEnd);

    void cancelByOwner(long ownerId, long appointmentId);

    List<Appointment> getDoctorAppointments(long doctorId);

    void updateStatusByDoctor(long doctorId, long appointmentId, AppointmentStatus status);
}

