package service;

import domain.Appointment;
import domain.AppointmentStatus;
import domain.Doctor;
import domain.Pet;
import repository.AppointmentRepository;
import repository.DoctorRepository;
import repository.PetRepository;

import java.time.OffsetDateTime;
import java.util.List;

import static service.validator.TimeValidator.validateTime;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  PetRepository petRepository,
                                  DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void book(long ownerId,
                     long petId,
                     long doctorId,
                     OffsetDateTime start,
                     OffsetDateTime end,
                     String reason) {

        validateTime(start, end);

        Pet pet = petRepository.findById(petId);
        if (pet == null)
            throw new IllegalArgumentException("Питомец не найден: id=" + petId);

        if (pet.getOwnerId() != ownerId)
            throw new IllegalStateException("Нельзя записывать чужого питомца");

        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null)
            throw new IllegalArgumentException("Доктор не найден: id=" + doctorId);

        boolean conflict = appointmentRepository.existsOverlapping(doctorId, start, end);
        if (conflict)
            throw new IllegalStateException("Это время уже занято у врача");

        Appointment appointment = new Appointment(
                0L,
                petId,
                doctorId,
                start,
                end,
                reason,
                AppointmentStatus.PLANNED
        );

        appointmentRepository.insert(appointment);

    }

    @Override
    public void rescheduleByOwner(long ownerId, long appointmentId, OffsetDateTime newStart, OffsetDateTime newEnd) {
        validateTime(newStart, newEnd);

        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null)
            throw new IllegalArgumentException("Запись не найдена");

        Pet pet = petRepository.findById(appointment.getPetId());
        if (pet == null || pet.getOwnerId() != ownerId)
            throw new IllegalStateException("Нельзя менять чужую запись");

        if (appointment.getStatus() == AppointmentStatus.DONE ||
                appointment.getStatus() == AppointmentStatus.CANCELLED)
            throw new IllegalStateException("Эту запись уже нельзя изменить");

        boolean conflict = appointmentRepository.existsOverlapping(appointment.getDoctorId(), newStart, newEnd);
        if (conflict)
            throw new IllegalStateException("Новое время занято");

        appointment.setStartsAt(newStart);
        appointment.setEndsAt(newEnd);

        appointmentRepository.update(appointmentId, appointment);
    }

    @Override
    public void cancelByOwner(long ownerId, long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null)
            throw new IllegalArgumentException("Запись не найдена");

        Pet pet = petRepository.findById(appointment.getPetId());
        if (pet == null || pet.getOwnerId() != ownerId)
            throw new IllegalStateException("Нельзя отменять чужую запись");

        if (appointment.getStatus() == AppointmentStatus.DONE)
            throw new IllegalStateException("Нельзя отменить завершённую запись");

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentRepository.update(appointmentId, appointment);
    }

    @Override
    public List<Appointment> getDoctorAppointments(long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public void updateStatusByDoctor(long doctorId, long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null)
            throw new IllegalArgumentException("Запись не найдена");

        if (appointment.getDoctorId() != doctorId)
            throw new IllegalStateException("Нельзя менять чужую запись");

        appointment.setStatus(status);

        appointmentRepository.update(appointmentId, appointment);
    }
}
