package service;

import domain.*;

import java.util.List;

public interface AdminService {

    // ---- Owners ----
    void addOwner(Owner owner);
    void updateOwner(long ownerId, Owner newOwner);
    void deleteOwner(long ownerId);
    Owner getOwnerById(long ownerId);
    List<Owner> getAllOwners();

    // ---- Doctors ----
    void addDoctor(Doctor doctor);
    void updateDoctor(long doctorId, Doctor newDoctor);
    void deleteDoctor(long doctorId);
    Doctor getDoctorById(long doctorId);
    List<Doctor> getAllDoctors();

    // ---- Pets ----
    void addPet(Pet pet);
    void updatePet(long petId, Pet newPet);
    void deletePet(long petId);
    Pet getPetById(long petId);
    List<Pet> getAllPets();

    // ---- Appointments ----
    void addAppointment(Appointment appointment);
    void updateAppointment(long appointmentId, Appointment newAppointment);
    void deleteAppointment(long appointmentId);
    Appointment getAppointmentById(long appointmentId);
    List<Appointment> getAllAppointments();
}
