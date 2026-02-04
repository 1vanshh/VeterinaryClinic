package service;

import domain.*;
import repository.*;

import java.util.List;

import static service.validator.TimeValidator.validateTime;

public class AdminServiceImpl implements AdminService {

    private final OwnerRepository ownerRepository;
    private final DoctorRepository doctorRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminServiceImpl(OwnerRepository ownerRepository,
                            DoctorRepository doctorRepository,
                            PetRepository petRepository,
                            AppointmentRepository appointmentRepository) {
        this.ownerRepository = ownerRepository;
        this.doctorRepository = doctorRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Owners

    @Override
    public void addOwner(Owner owner) {
        ownerRepository.insert(owner);
    }

    @Override
    public void updateOwner(long ownerId, Owner newOwner) {
        ensureOwnerExists(ownerId);
        ownerRepository.update(ownerId, newOwner);
    }

    @Override
    public void deleteOwner(long ownerId) {
        ensureOwnerExists(ownerId);
        ownerRepository.deleteById(ownerId);
    }

    @Override
    public Owner getOwnerById(long ownerId) {
        Owner owner = ownerRepository.findById(ownerId);
        if (owner == null) throw new IllegalArgumentException("Владелец не найден: id=" + ownerId);
        return owner;
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    // Doctors

    @Override
    public void addDoctor(Doctor doctor) {
        doctorRepository.insert(doctor);
    }

    @Override
    public void updateDoctor(long doctorId, Doctor newDoctor) {
        ensureDoctorExists(doctorId);
        doctorRepository.update(doctorId, newDoctor);
    }

    @Override
    public void deleteDoctor(long doctorId) {
        ensureDoctorExists(doctorId);
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public Doctor getDoctorById(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor == null) throw new IllegalArgumentException("Доктор не найден: id=" + doctorId);
        return doctor;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Pets

    @Override
    public void addPet(Pet pet) {
        // Админ может добавить питомца кому угодно, но ownerId должен существовать
        ensureOwnerExists(pet.getOwnerId());
        petRepository.insert(pet);
    }

    @Override
    public void updatePet(long petId, Pet newPet) {
        ensurePetExists(petId);

        ensureOwnerExists(newPet.getOwnerId());
        petRepository.update(petId, newPet);
    }

    @Override
    public void deletePet(long petId) {
        ensurePetExists(petId);
        petRepository.deleteById(petId);
    }

    @Override
    public Pet getPetById(long petId) {
        Pet pet = petRepository.findById(petId);
        if (pet == null) throw new IllegalArgumentException("Питомец не найден: id=" + petId);
        return pet;
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Appointments

    @Override
    public void addAppointment(Appointment appointment) {
        validateTime(appointment.getStartsAt(), appointment.getEndsAt());

        ensurePetExists(appointment.getPetId());
        ensureDoctorExists(appointment.getDoctorId());

        if (appointment.getStatus() != AppointmentStatus.CANCELLED) {
            boolean conflict = appointmentRepository.existsOverlapping(
                    appointment.getDoctorId(),
                    appointment.getStartsAt(),
                    appointment.getEndsAt()
            );

            if (conflict) {
                throw new IllegalStateException("Это время уже занято у врача");
            }
        }

        appointmentRepository.insert(appointment);
    }

    @Override
    public void updateAppointment(long appointmentId, Appointment newAppointment) {
        ensureAppointmentExists(appointmentId);
        ensurePetExists(newAppointment.getPetId());
        ensureDoctorExists(newAppointment.getDoctorId());

        validateTime(newAppointment.getStartsAt(), newAppointment.getEndsAt());

        if (newAppointment.getStatus() != AppointmentStatus.CANCELLED && newAppointment.getStatus() != AppointmentStatus.DONE) {

            boolean conflict = appointmentRepository.existsOverlapping(
                    newAppointment.getDoctorId(),
                    newAppointment.getStartsAt(),
                    newAppointment.getEndsAt()
            );

            if (conflict) {
                throw new IllegalStateException("Это время уже занято у врача");
            }
        }

        appointmentRepository.update(appointmentId, newAppointment);
    }

    @Override
    public void deleteAppointment(long appointmentId) {
        ensureAppointmentExists(appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public Appointment getAppointmentById(long appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId);
        if (appt == null) throw new IllegalArgumentException("Запись не найдена: id=" + appointmentId);
        return appt;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Helpers

    private void ensureOwnerExists(long ownerId) {
        if (ownerRepository.findById(ownerId) == null) {
            throw new IllegalArgumentException("Владелец не найден: id=" + ownerId);
        }
    }

    private void ensureDoctorExists(long doctorId) {
        if (doctorRepository.findById(doctorId) == null) {
            throw new IllegalArgumentException("Доктор не найден: id=" + doctorId);
        }
    }

    private void ensurePetExists(long petId) {
        if (petRepository.findById(petId) == null) {
            throw new IllegalArgumentException("Питомец не найден: id=" + petId);
        }
    }

    private void ensureAppointmentExists(long appointmentId) {
        if (appointmentRepository.findById(appointmentId) == null) {
            throw new IllegalArgumentException("Запись не найдена: id=" + appointmentId);
        }
    }
}
